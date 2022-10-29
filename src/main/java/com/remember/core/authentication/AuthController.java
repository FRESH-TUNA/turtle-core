package com.remember.core.authentication;

import com.remember.core.authentication.authToken.AuthTokenProvider;
import com.remember.core.authentication.authToken.AuthTokenResponseDto;
import com.remember.core.authentication.dtos.LoginRequest;
import com.remember.core.authentication.dtos.UserRequest;
import com.remember.core.authentication.services.AuthService;
import com.remember.core.controllers.AbstractController;
import com.remember.core.dtos.responses.BaseResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController extends AbstractController {
    private final AuthService authService;
    private final AuthTokenProvider authTokenProvider;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @RequestBody UserRequest userRequest) {

        AuthTokenResponseDto authTokenResponseDto = authService.registerNewUserAccount(userRequest);

        this.addCookie(
                response,
                authTokenProvider.REFRESH_TOKEN_COOKIE_KEY,
                authTokenResponseDto.getRefreshToken(),
                (int) authTokenProvider.REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authTokenResponseDto.getAccessToken())
                .body(BaseResponse.OK);
    }


    /** 로그인 */
    @PostMapping(path = "/signin")
    public ResponseEntity<?> login(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestBody LoginRequest loginRequest) {

        AuthTokenResponseDto authTokenResponseDto = authService.login(loginRequest);

        this.addCookie(
                response,
                authTokenProvider.REFRESH_TOKEN_COOKIE_KEY,
                authTokenResponseDto.getRefreshToken(),
                (int) authTokenProvider.REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authTokenResponseDto.getAccessToken())
                .body(BaseResponse.OK);
    }

    /** 로그인 */
    @PostMapping(path = "/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {

        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshToken = this.getValueFromCokkie(request, authTokenProvider.REFRESH_TOKEN_COOKIE_KEY);
        AuthTokenResponseDto dto = authService.refreshAccessToken(accessToken, refreshToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, dto.getAccessToken())
                .body(BaseResponse.OK);
    }
}
