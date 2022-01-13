package xyz.bumbing.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

@Getter
public class JwtAuthentication extends UsernamePasswordAuthenticationToken {

    private final boolean expired;
    private final Map<String, Object> payload;

    public JwtAuthentication(Long memberId, Collection<? extends GrantedAuthority> authorities, Map<String, Object> payload, boolean expired) {
        super(memberId, null, authorities);
        this.payload = payload;
        this.expired = expired;

    }
}
