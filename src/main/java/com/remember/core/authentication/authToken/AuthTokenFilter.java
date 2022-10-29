package com.remember.core.authentication.authToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String access = resolveAccessTokenFromRequest(request);
        String refresh = resolveRefreshTokenFromRequest(request);

        if (!StringUtils.hasText(access)) {
            log.info("토큰 정보 없음");
        }
        else {
            /* token string -> AuthToken object */
            UserDetails accessDetail = tokenProvider.accessTokenToUserIdentity(access, false);
            UserDetails refreshDetail = tokenProvider.refreshTokenToUserIdentity(refresh);

            if (Objects.nonNull(accessDetail) && Objects.nonNull(refreshDetail)
                    && accessDetail.getUsername().equals(refreshDetail.getUsername())) {

                /* jwt token은 패스워드가 없다. */
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        accessDetail, "", accessDetail.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Security Context => 인증 정보 저장 완료: {}", accessDetail.getUsername());
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * helpers
     */
    private String resolveAccessTokenFromRequest(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    private String resolveRefreshTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AuthTokenProvider.REFRESH_TOKEN_COOKIE_KEY)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
