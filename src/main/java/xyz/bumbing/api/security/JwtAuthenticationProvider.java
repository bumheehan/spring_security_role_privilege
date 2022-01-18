package xyz.bumbing.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import xyz.bumbing.api.config.AppTokenConfig;
import xyz.bumbing.api.security.dto.SingleTokenDto;
import xyz.bumbing.api.security.type.JwtType;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProvider {
    private Key accessKey;
    private Key refreshKey;
    private Long accessExpiresIn;
    private Long refreshExpireIn;

    private static final String PAYLOAD_USER_KEY = "uid";
    private static final String PAYLOAD_ROLE_KEY = "role";
    private final AppTokenConfig appTokenConfig;

    @PostConstruct
    private void init() {
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


    public SingleTokenDto generateAccessToken(List<String> role, Long uid) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put(PAYLOAD_ROLE_KEY, String.join(",", role));
            payload.put(PAYLOAD_USER_KEY, uid);
            Claims claims = Jwts.claims(payload);
            Instant now = Instant.now();
            Date issueAt = Date.from(now);

            SingleTokenDto tokenDto = new SingleTokenDto();
            Date expireAt = Date.from(now.plusSeconds(this.accessExpiresIn));
            tokenDto.setToken(Jwts.builder().setClaims(claims).setIssuedAt(issueAt).setExpiration(expireAt).signWith(this.accessKey, SignatureAlgorithm.HS256).compact());
            tokenDto.setExpiresIn(Long.toString(this.accessExpiresIn));
            return tokenDto;
        } catch (Exception e) {
            throw new IllegalStateException("failed to generate jwt");
        }
    }

    public SingleTokenDto generateRefreshToken(List<String> role, Long uid) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put(PAYLOAD_ROLE_KEY, String.join(",", role));
            payload.put(PAYLOAD_USER_KEY, uid);
            Claims claims = Jwts.claims(payload);
            Instant now = Instant.now();
            Date issueAt = Date.from(now);

            SingleTokenDto tokenDto = new SingleTokenDto();
            Date expireAt = Date.from(now.plusSeconds(this.refreshExpireIn));
            tokenDto.setToken(Jwts.builder().setClaims(claims).setIssuedAt(issueAt).setExpiration(expireAt).signWith(this.refreshKey, SignatureAlgorithm.HS256).compact());
            tokenDto.setExpiresIn(Long.toString(this.refreshExpireIn));
            return tokenDto;
        } catch (Exception e) {
            throw new IllegalStateException("failed to generate jwt");
        }
    }

    public Authentication getAuthenticationByAccessToken(String accessToken) throws Exception {
        Claims body = Jwts.parserBuilder().setSigningKey(this.accessKey).build().parseClaimsJws(accessToken).getBody();
        List<String> role = List.of(((String) body.get(PAYLOAD_ROLE_KEY)).split(","));
        Object uid = body.get(PAYLOAD_USER_KEY);
        Collection<GrantedAuthority> authorities = role.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(uid, null, authorities);
    }
}