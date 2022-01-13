package xyz.bumbing.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import xyz.bumbing.api.config.AppTokenConfig;
import xyz.bumbing.api.security.dto.SingleTokenDto;
import xyz.bumbing.api.security.type.JwtType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {
    private Key accessKey;
    private Key refreshKey;
    private Long accessExpiresIn;
    private Long refreshExpireIn;

    private final AppTokenConfig appTokenConfig;

    @PostConstruct
    private void init(){
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(appTokenConfig.getAccessSecret()));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(appTokenConfig.getRefreshSecret()));
        this.accessExpiresIn = appTokenConfig.getAccessExpired();
        this.refreshExpireIn = appTokenConfig.getRefreshExpired();
    }

    public TokenValidDto validateToken(String token, JwtType tokenType) {
        TokenValidDto tokenValid = new TokenValidDto();
        try {
            tokenValid.setPayload(Jwts.parserBuilder().setSigningKey(JwtType.ACCESS.equals(tokenType) ? this.accessKey : this.refreshKey).build().parseClaimsJws(token).getBody());
        } catch (ExpiredJwtException e) {
            tokenValid.setExpired(true);
            tokenValid.setPayload(e.getClaims());
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid token" + token, e);
        }
        return tokenValid;
    }


    @Data
    public static class TokenValidDto {
        private boolean expired;
        private Map<String, Object> payload;
    }


    public SingleTokenDto generateToken(Map<String, Object> payload, JwtType tokenType) {
        try {
            Claims claims = Jwts.claims(payload);
            Instant now = Instant.now();
            Date issueAt = Date.from(now);

            SingleTokenDto tokenDto = new SingleTokenDto();
            if (JwtType.ACCESS.equals(tokenType)) {
                Date expireAt = Date.from(now.plusSeconds(this.accessExpiresIn));
                tokenDto.setToken(Jwts.builder().setClaims(claims).setIssuedAt(issueAt).setExpiration(expireAt).signWith(this.accessKey, SignatureAlgorithm.HS256).compact());
                tokenDto.setExpiresIn(Long.toString(this.accessExpiresIn));
            } else {
                Date expireAt = Date.from(now.plusSeconds(this.refreshExpireIn));
                tokenDto.setToken(Jwts.builder().setClaims(claims).setIssuedAt(issueAt).setExpiration(expireAt).signWith(this.refreshKey, SignatureAlgorithm.HS256).compact());
                tokenDto.setExpiresIn(Long.toString(this.refreshExpireIn));
            }
            return tokenDto;
        } catch (Exception e) {
            throw new IllegalStateException("failed to generate jwt");
        }
    }


}