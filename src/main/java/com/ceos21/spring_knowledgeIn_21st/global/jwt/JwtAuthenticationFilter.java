package com.ceos21.spring_knowledgeIn_21st.global.jwt;

import com.ceos21.spring_knowledgeIn_21st.domain.auth.dto.request.SigninRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // 로그인 요청 처리 (POST /api/auth/signin)
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            // JSON -> LoginRequest(email, password)
            SigninRequest signinRequest = new ObjectMapper().readValue(request.getInputStream(), SigninRequest.class);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword());

            // AuthenticationManager에게 인증 위임
            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException("로그인 요청 파싱 실패", e);
        }
    }

    // 인증 성공 시: JWT 토큰 발급 & 응답 헤더에 추가
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        SecurityContextHolder.getContext().setAuthentication(authResult);   // 인증 정보 저장
        chain.doFilter(request, response);
    }

    // 인증 실패 시 처리
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\":\"로그인 실패: " + failed.getMessage() + "\"}");
    }
}