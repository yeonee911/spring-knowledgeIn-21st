package com.ceos21.spring_knowledgeIn_21st.global.advice;

import com.ceos21.spring_knowledgeIn_21st.global.common.ApiResponse;
import com.ceos21.spring_knowledgeIn_21st.global.jwt.JwtAuthenticationFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // Swagger 문서 요청일 경우 제외
        String className = returnType.getDeclaringClass().getName();
        return !className.startsWith("org.springdoc")  // springdoc 관련은 제외
                && !returnType.getParameterType().equals(ApiResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if (body instanceof ApiResponse) return body; // 이미 래핑돼 있으면 그대로

        // String인 경우에는 별도 처리 (Jackson 변환 오류 방지)
        if (body instanceof String) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writer().writeValueAsString(ApiResponse.success(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("JSON 변환 실패", e);
            }
        }

        return ApiResponse.success(body);
    }
}
