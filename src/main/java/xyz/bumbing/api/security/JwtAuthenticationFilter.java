package xyz.bumbing.api.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String TOKEN_TYPE = "Bearer";

    private final JwtAuthenticationProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            if (token.startsWith(TOKEN_TYPE)) {
                try {
                    Authentication authentication = jwtProvider.getAuthenticationByAccessToken(token.substring(7));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (Exception e) {
                    log.error("jwt parse error : " + token, e);
                }
            }
        }
        this.doFilter(request, response, filterChain);
    }
}
