package xyz.bumbing.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xyz.bumbing.api.security.CustomAccessDeniedHandler;
import xyz.bumbing.api.security.CustomAuthenticationEntryPoint;
import xyz.bumbing.api.security.JwtAuthenticationFilter;
import xyz.bumbing.api.security.JwtAuthenticationProvider;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtAuthenticationProvider jwtUtils;

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
                .headers().frameOptions().disable().and() // h2-console 사용안할때 제거
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션을 사용하지 않겠다고 선언
                .and()
                .exceptionHandling().authenticationEntryPoint(this.customAuthenticationEntryPoint).accessDeniedHandler(this.customAccessDeniedHandler)
                .and()
                .addFilterAt(new JwtAuthenticationFilter(this.jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/test/user/config").hasRole("USER")
                .antMatchers("/api/test/admin/config").hasRole("ADMIN");
    }
}

