package com.ceos21.spring_knowledgeIn_21st.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {  // 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // H2 콘솔 허용
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS)) // 기본 설정된 Session 방식은 사용하지 않고 JWT 방식을 사용
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                        .requestMatchers("/", "/swagger-ui/**", "/v3/**").permitAll()   // 메인 페이지, Swagger
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll() // H2 콘솔
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .build();
    }
}
