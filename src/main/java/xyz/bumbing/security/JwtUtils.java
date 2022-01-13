package xyz.bumbing.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import xyz.bumbing.security.dto.SingleTokenDto;
import xyz.bumbing.security.type.JwtType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtUtils {
    private final Key accessKey;
    private final Key refreshKey;
    private final Long accessExpiresIn;
    private final Long refreshExpireIn;

    public JwtUtils(@Value("${mora.token.access.secret}") String accessSecretKey,
                    @Value("${mora.token.refresh.secret}") String refreshSecretKey,
                    @Value("${mora.token.access.expired:1800}") String accessExpired,
                    @Value("${mora.token.refresh.expired:604800}") String refreshExpired) {

        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecretKey));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecretKey));
        this.accessExpiresIn = Long.parseLong(accessExpired);
        this.refreshExpireIn = Long.parseLong(refreshExpired);
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