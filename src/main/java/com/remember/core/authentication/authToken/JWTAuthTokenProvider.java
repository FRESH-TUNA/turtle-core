package com.remember.core.authentication.authToken;

import com.remember.core.authentication.dtos.CentralAuthenticatedUser;
import com.remember.core.authentication.dtos.RememberUser;
import com.remember.core.authentication.properties.RememberJwtAuthProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class JWTAuthTokenProvider {
    private final RememberJwtAuthProperties jwtAuthProperties;

    private final Key KEY;
    private final Key REFRESH_TOKEN_KEY;

    public final Long TOKEN_VALIDITY_IN_MILLISECONDS;
    public final Long REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS;

    public static final String AUTHORITIES_KEY = "role";

    public JWTAuthTokenProvider(RememberJwtAuthProperties jwtAuthProperties) {
        this.jwtAuthProperties = jwtAuthProperties;

        this.KEY = Keys.hmacShaKeyFor(jwtAuthProperties.getSecret().getBytes());
        this.REFRESH_TOKEN_KEY = Keys.hmacShaKeyFor(jwtAuthProperties.getRefreshSecret().getBytes());

        this.TOKEN_VALIDITY_IN_MILLISECONDS = jwtAuthProperties.getValidityInSeconds() * 1000;
        this.REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS = jwtAuthProperties.getRefreshValidityInSeconds() * 1000;
    }

    public String generateToken(RememberUser user) {
        String subject = user.getId().toString();
        long now = new Date().getTime();
        Date expiryDate = new Date(now + TOKEN_VALIDITY_IN_MILLISECONDS);

        return jwtAuthProperties.getTokenPrefix() + Jwts.builder()
                .setSubject(subject)
                .claim(AUTHORITIES_KEY, user.getConcatenatedAuthoritiesByComma())
                .signWith(KEY, SignatureAlgorithm.HS512)
                .setExpiration(expiryDate)
                .compact();
    }

    /** Refresh Token string 생성 */
    public String generateRefreshToken(String subject) {
        long now = new Date().getTime();
        Date expiryDate = new Date(now + REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS);

        return Jwts.builder()
                .setSubject(subject)
                .signWith(REFRESH_TOKEN_KEY, SignatureAlgorithm.HS512)
                .setExpiration(expiryDate)
                .compact();
    }

    /** token string 을 AuthToken 객체로 변환 */
    /** refresh목적으로 access 토큰 검증시 expiredAllow를 true로 설정 **/
    public CentralAuthenticatedUser accessTokenToUserIdentity(String tokenString, boolean expiredAllow) {
        return convertToUserIdentity(tokenString, KEY, expiredAllow);
    }

    public CentralAuthenticatedUser refreshTokenToUserIdentity(String tokenString) {
        return convertToUserIdentity(tokenString, REFRESH_TOKEN_KEY, false);
    }

    public CentralAuthenticatedUser convertToUserIdentity(String tokenString, Key key, boolean expiredAllow) {
        if(Objects.isNull(tokenString) || tokenString.isBlank())
            return null;

        if(tokenString.startsWith(jwtAuthProperties.getTokenPrefix()))
            tokenString = tokenString.substring(jwtAuthProperties.getTokenPrefix().length());

        try {
            return CentralAuthenticatedUser.of(
                    Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(tokenString)
                    .getBody()
            );
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            if(expiredAllow)
                return CentralAuthenticatedUser.of(e.getClaims());
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        } catch (RuntimeException e) {
            log.info(e.getMessage());
        }
        return null;
    }
}
