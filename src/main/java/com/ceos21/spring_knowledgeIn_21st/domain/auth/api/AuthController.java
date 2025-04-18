package com.ceos21.spring_knowledgeIn_21st.domain.auth.api;

import com.ceos21.spring_knowledgeIn_21st.domain.auth.dto.response.SigninResponse;
import com.ceos21.spring_knowledgeIn_21st.domain.user.application.UserService;
import com.ceos21.spring_knowledgeIn_21st.domain.auth.dto.request.SigninRequest;
import com.ceos21.spring_knowledgeIn_21st.domain.auth.dto.request.SignupRequest;
import com.ceos21.spring_knowledgeIn_21st.global.common.ApiResponse;
import com.ceos21.spring_knowledgeIn_21st.global.exception.CustomException;
import com.ceos21.spring_knowledgeIn_21st.global.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.ceos21.spring_knowledgeIn_21st.global.exception.ErrorCode.INVALID_ACCESS;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Operation(
            summary = "회원가입",
            description = "새로운 사용자를 등록합니다."
    )
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody SignupRequest request) {
        userService.register(request);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(null));
    }

    @Operation(
            summary = "로그인",
            description = "이메일과 비밀번호로 로그인합니다. 실제 처리는 JwtAuthenticationFilter에서 수행"
    )
    @PostMapping("/signin")
    public void signin(@RequestBody SigninRequest request) {
    }
}
