package com.ceos21.spring_knowledgeIn_21st.global.config;

import com.ceos21.spring_knowledgeIn_21st.global.jwt.*;
import com.ceos21.spring_knowledgeIn_21st.global.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {  // 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public RequestBodyCachingFilter requestBodyCachingFilter() {
        return new RequestBodyCachingFilter();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager, RefreshTokenRepository refreshTokenRepository) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtUtil, refreshTokenRepository);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/auth/signin");

        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(HttpMethod.GET, "/posts").permitAll()
                        .requestMatchers(HttpMethod.POST, "/posts").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/posts").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/posts").authenticated()
                        .requestMatchers("/",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/api/auth/signup",
                                "/api/auth/signin",
                                "/api/auth/refresh",
                                "/posts/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // 1) 캐싱 필터를 인증 필터(UsernamePasswordAuthenticationFilter) 앞에 등록
                .addFilterBefore(
                        requestBodyCachingFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )

                // 2) JWT 로그인 처리 필터 등록
               .addFilterBefore(
                       jwtAuthenticationFilter,
                       UsernamePasswordAuthenticationFilter.class
               )

                // 3) JWT 인가(Authorization) 필터 등록은 인증 필터 이후에
                .addFilterAfter(
                        new JwtAuthorizationFilter(jwtUtil, userDetailsService),
                        JwtAuthenticationFilter.class
                );

        return http.build();
    }
}
