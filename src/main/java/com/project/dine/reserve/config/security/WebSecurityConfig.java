package com.project.dine.reserve.config.security;

import com.project.dine.reserve.config.security.filter.ExceptionHandlerFilter;
import com.project.dine.reserve.config.security.filter.JWTFilter;
import com.project.dine.reserve.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JWTUtil jwtUtil;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    private final String SWAGGER_API = "/swagger-ui/**";
    private final String SWAGGER_HTML_API = "/swagger-ui.html";
    private final String V3_DOCKS_API = "/v3/api-docs/**";
    private final String MEMBER_SIGNUP = "/api/dine/reserve/member/signup";

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.ignoring().requestMatchers(
                SWAGGER_API,
                SWAGGER_HTML_API,
                V3_DOCKS_API,
                MEMBER_SIGNUP
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JWTFilter.class)
                .build();
    }
}
