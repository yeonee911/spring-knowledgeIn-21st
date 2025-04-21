package com.ceos21.spring_knowledgeIn_21st.domain.answer.api;

import com.ceos21.spring_knowledgeIn_21st.domain.answer.application.AnswerService;
import com.ceos21.spring_knowledgeIn_21st.domain.answer.domain.Answer;
import com.ceos21.spring_knowledgeIn_21st.domain.answer.dto.request.AnswerAddRequest;
import com.ceos21.spring_knowledgeIn_21st.domain.answer.dto.response.AnswerDetailResponse;
import com.ceos21.spring_knowledgeIn_21st.global.common.ApiResponse;
import com.ceos21.spring_knowledgeIn_21st.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/{postId}/answers")
    @SecurityRequirement(name = "Authorization")
    @Operation(
            summary = "답변 추가",
            description = "새로운 답변을 등록합니다"
    )
    public ResponseEntity<ApiResponse<AnswerDetailResponse>> saveAnswer(
            @PathVariable Long postId,
            @RequestBody AnswerAddRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetail
    ) {
        Answer savedAnswer = answerService.saveAnswer(request, postId, userDetail.getUserId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(AnswerDetailResponse.from(savedAnswer)));
    }

    @GetMapping("/answers/{answerId}")
    @Operation(
            summary = "답변 조회",
            description = "답변을 조회합니다"
    )
    public ResponseEntity<ApiResponse<AnswerDetailResponse>> getAnswer(
            @PathVariable Long answerId
    ){
        Answer answer = answerService.findAnswerById(answerId);
        AnswerDetailResponse response = AnswerDetailResponse.from(answer);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
