package com.remember.core.authentication.services;

import com.remember.core.authentication.authToken.AuthTokenResponseDto;
import com.remember.core.authentication.domains.ProviderType;
import com.remember.core.authentication.domains.RefreshToken;
import com.remember.core.authentication.dtos.*;
import com.remember.core.authentication.authToken.AuthTokenProvider;
import com.remember.core.authentication.repositories.RefreshTokenRepository;
import com.remember.core.exceptions.ErrorCode;
import com.remember.core.authentication.repositories.UserRepository;
import com.remember.core.authentication.domains.User;

import com.remember.core.exceptions.RememberException;
import com.remember.core.utils.AuthenticatedFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final AuthTokenProvider authTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final AuthenticatedFacade authenticatedFacade;

    @Transactional
    public AuthTokenResponseDto login(LoginRequest request)  {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        authenticatedFacade.checkIsAuthenticated(authenticate);
        CentralAuthenticatedUser identity = ((CentralAuthenticatedUser) authenticate.getPrincipal());

        String accessToken = authTokenProvider.generateToken(identity);
        String refreshToken = authTokenProvider.generateRefreshToken(identity.getUsername());

        User user = userRepository.getById(identity.getId());
        Optional<RefreshToken> refreshTokenEntity = refreshTokenRepository.findByUser(user);

        if(refreshTokenEntity.isPresent()) {
            refreshTokenEntity.get().updateToken(refreshToken);
        } else {
            refreshTokenRepository.save(RefreshToken.builder().token(refreshToken).user(user).build());
        }

        return AuthTokenResponseDto.ofLogin(accessToken, refreshToken);
    }

    @Transactional
    public AuthTokenResponseDto registerNewUserAccount(UserRequest userRequest)  {
        User user = userRequest.toLocalGuestUser();

        if(userRepository.findByUsernameAndProviderType(userRequest.getEmail(), ProviderType.LOCAL).isPresent())
            throw new RememberException(ErrorCode.SAME_EMAIL_EXSITED);

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        userRepository.save(user);

        return this.login(userRequest.toLoginRequest());
    }

    public AuthTokenResponseDto refreshAccessToken(String accessTokenString, String refreshTokenString) {
        RememberUser access = authTokenProvider.accessTokenToUserIdentity(accessTokenString, true);
        RememberUser refresh = authTokenProvider.refreshTokenToUserIdentity(refreshTokenString);

        if (Objects.isNull(access) || Objects.isNull(refresh) || !access.isSameUser(refresh))
            throw new RememberException(ErrorCode.BAD_REQUEST);

        return AuthTokenResponseDto.ofAccess(authTokenProvider.generateToken(access));
    }
}
