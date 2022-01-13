package xyz.bumbing.api.security;

import xyz.bumbing.api.security.type.JwtType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String TOKEN_TYPE = "Bearer";
    private static final String PAYLOAD_USER_KEY = "member";
    private static final String PAYLOAD_ROLE_KEY = "role";
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            if (token.startsWith(TOKEN_TYPE)) {
                JwtUtils.TokenValidDto tokenValidDto = this.jwtUtils.validateToken(token.substring(7), JwtType.ACCESS);
                if (!tokenValidDto.isExpired()) {
                    Map<String, Object> payload = tokenValidDto.getPayload();
                    Long member = ((Number) payload.get(PAYLOAD_USER_KEY)).longValue();
                    String auth = (String) payload.get(PAYLOAD_ROLE_KEY);
                    Collection<GrantedAuthority> authorities = Arrays.stream(auth.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                    JwtAuthentication authentication = new JwtAuthentication(member, authorities, payload, tokenValidDto.isExpired());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        this.doFilter(request, response, filterChain);
    }
}
