package xyz.bumbing.api.config;

import xyz.bumbing.api.security.CustomAccessDeniedHandler;
import xyz.bumbing.api.security.CustomAuthenticationEntryPoint;
import xyz.bumbing.api.security.JwtAuthenticationFilter;
import xyz.bumbing.api.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtUtils jwtUtils;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {


        httpSecurity
                .cors().and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션을 사용하지 않겠다고 선언
                .and()
                .exceptionHandling().authenticationEntryPoint(this.customAuthenticationEntryPoint).accessDeniedHandler(this.customAccessDeniedHandler)
                .and()
                .addFilterAt(new JwtAuthenticationFilter(this.jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/member/test2").authenticated()
                .antMatchers("/").permitAll();

    }
}
