package com.ceos21.spring_knowledgeIn_21st.domain.auth.api;

import com.ceos21.spring_knowledgeIn_21st.domain.user.application.UserService;
import com.ceos21.spring_knowledgeIn_21st.domain.user.dto.request.LoginRequest;
import com.ceos21.spring_knowledgeIn_21st.domain.user.dto.request.SignupRequest;
import com.ceos21.spring_knowledgeIn_21st.global.common.ApiResponse;
import com.ceos21.spring_knowledgeIn_21st.global.exception.CustomException;
import com.ceos21.spring_knowledgeIn_21st.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ceos21.spring_knowledgeIn_21st.global.exception.ErrorCode.INVALID_ACCESS;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @Operation(
            summary = "회원가입",
            description = "새로운 사용자를 등록합니다."
    )
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@RequestBody SignupRequest request) {
        userService.register(request);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(null));
    }

    @Operation(
            summary = "로그인",
            description = "이메일과 비밀번호로 로그인합니다. 실제 처리는 JwtAuthenticationFilter에서 수행됩니다."
    )
    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody LoginRequest request) {
        String token = userService.login(request);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body("success");
    }
}
