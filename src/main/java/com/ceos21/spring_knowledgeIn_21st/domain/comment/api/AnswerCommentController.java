package com.ceos21.spring_knowledgeIn_21st.domain.comment.api;

import com.ceos21.spring_knowledgeIn_21st.domain.comment.application.AnswerCommentService;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.AnswerComment;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.dto.response.CommentResponse;
import com.ceos21.spring_knowledgeIn_21st.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AnswerCommentController {
    private final AnswerCommentService answerCommentService;

    @GetMapping("/answers/{answerId}/comments")
    @Operation(
            summary = "답변 댓글 조회",
            description = "답변의 전체 댓글을 조회합니다"
    )
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getAllComments(
            @PathVariable Long answerId
    ) {
        List<AnswerComment> comments = answerCommentService.findAllComments(answerId);
        if (comments.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(List.of(), "등록된 댓글이 없습니다"));
        }
        List<CommentResponse> responses = comments.stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
}
