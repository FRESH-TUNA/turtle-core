package com.remember.core.authentication.OAuth;


import com.remember.core.authentication.authToken.JWTAuthTokenProvider;
import com.remember.core.authentication.domains.RefreshToken;
import com.remember.core.authentication.domains.User;
import com.remember.core.authentication.dtos.RememberUser;
import com.remember.core.authentication.properties.RememberJwtAuthProperties;
import com.remember.core.authentication.properties.RememberOAuthProperties;
import com.remember.core.authentication.repositories.RefreshTokenRepository;
import com.remember.core.authentication.repositories.UserRepository;
import com.remember.core.authentication.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.remember.core.authentication.OAuth.HttpCookieOAuth2AuthorizationRequestRepository.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME;
import static com.remember.core.authentication.OAuth.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTAuthTokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private final RememberJwtAuthProperties rememberJwtAuthProperties;
    private final RememberOAuthProperties rememberOAuthProperties;


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException {

        log.info("1");
        if (response.isCommitted()) {
            return;
        }

        RememberUser rememberUser = ((RememberUser) authentication.getPrincipal());
        String accessToken = tokenProvider.generateToken(rememberUser);
        String refreshToken = tokenProvider.generateRefreshToken(String.valueOf(rememberUser.getId()));

        User user = userRepository.getById(rememberUser.getId());
        Optional<RefreshToken> refreshTokenEntity = refreshTokenRepository.findByUser(user);

        if(refreshTokenEntity.isPresent()) {
            refreshTokenEntity.get().updateToken(refreshToken);
        } else {
            refreshTokenRepository.save(RefreshToken.builder().token(refreshToken).user(user).build());
        }

        log.info("2");
        String targetUrl = determineTargetUrl(request, response, authentication);

        /* 복구된 쿠키 삭제 */
        clearAuthenticationAttributes(request, response);

        /* Refresh Token 쿠키 삭제 */
        CookieUtil.deleteCookie(request, response, rememberJwtAuthProperties.getRefreshTokenCookieKey());

        /* refreshToken 설정 */
        CookieUtil.addCookie(response,
                rememberJwtAuthProperties.getRefreshTokenCookieKey(),
                refreshToken,
                Math.toIntExact(rememberJwtAuthProperties.getValidityInSeconds()));

        CookieUtil.addCookie(response,
                             "ACCESS-TOKEN",
                             accessToken.substring(rememberJwtAuthProperties.getTokenPrefix().length()),
                             60);

        /* frontend redirect url 로 이동, 토큰 파라미터로 전달 */
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);


        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("허용되지 않은 Redirect URI.");
        }

        return redirectUri.orElse(getDefaultTargetUrl());
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return rememberOAuthProperties.getOAuth2AuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // host, port 검사
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort();
                });
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
    }
}
