package com.remember.core.authentication.authToken;

import com.remember.core.authentication.dtos.CentralAuthenticatedUser;
import com.remember.core.authentication.dtos.RememberUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class AuthTokenProvider {
    private final Key key;
    private final Key refreshTokenKey;
    public final long TOKEN_VALIDITY_IN_MILLISECONDS;
    public final long REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS;

    public static final String AUTHORITIES_KEY = "role";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String REFRESH_TOKEN_COOKIE_KEY = "REFRESH-TOKEN";

    public AuthTokenProvider(@Value("${jwt.secret}") String secret,
                             @Value("${jwt.refresh-token-secret}") String refreshTokenSecret,
                             @Value("${jwt.token-validity-in-seconds}") long validityInSeconds,
                             @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.refreshTokenKey = Keys.hmacShaKeyFor(refreshTokenSecret.getBytes());
        this.TOKEN_VALIDITY_IN_MILLISECONDS = validityInSeconds * 1000;
        this.REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS = refreshTokenValidityInSeconds * 1000;
    }

    public String generateToken(RememberUser user) {
        String subject = user.getId().toString();
        long now = new Date().getTime();
        Date expiryDate = new Date(now + TOKEN_VALIDITY_IN_MILLISECONDS);

        return TOKEN_PREFIX + Jwts.builder()
                .setSubject(subject)
                .claim(AUTHORITIES_KEY, user.getConcatenatedAuthoritiesByComma())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expiryDate)
                .compact();
    }

    /** Refresh Token string 생성 */
    public String generateRefreshToken(String subject) {
        long now = new Date().getTime();
        Date expiryDate = new Date(now + REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS);

        return Jwts.builder()
                .setSubject(subject)
                .signWith(refreshTokenKey, SignatureAlgorithm.HS512)
                .setExpiration(expiryDate)
                .compact();
    }

    /** token string 을 AuthToken 객체로 변환 */
    /** refresh목적으로 access 토큰 검증시 expiredAllow를 true로 설정 **/
    public CentralAuthenticatedUser accessTokenToUserIdentity(String tokenString, boolean expiredAllow) {
        return convertToUserIdentity(tokenString, key, expiredAllow);
    }

    public CentralAuthenticatedUser refreshTokenToUserIdentity(String tokenString) {
        return convertToUserIdentity(tokenString, refreshTokenKey, false);
    }

    public CentralAuthenticatedUser convertToUserIdentity(String tokenString, Key key, boolean expiredAllow) {
        if(Objects.isNull(tokenString) || tokenString.isBlank())
            return null;

        if(tokenString.startsWith(TOKEN_PREFIX))
            tokenString = tokenString.substring(TOKEN_PREFIX.length());

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
