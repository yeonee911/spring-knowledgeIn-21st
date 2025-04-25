package com.ceos21.spring_knowledgeIn_21st.domain.comment.dto.response;

import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.BaseComment;
import com.ceos21.spring_knowledgeIn_21st.domain.user.dto.reponse.UserSummaryResponse;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UserSummaryResponse user
) {
    public static CommentResponse from(BaseComment baseComment) {
        return new CommentResponse(
                baseComment.getId(),
                baseComment.getContent(),
                baseComment.getCreatedAt(),
                baseComment.getUpdatedAt(),
                new UserSummaryResponse(
                        baseComment.getUser().getId(),
                        baseComment.getUser().getName(),
                        baseComment.getUser().getEmail()
                )
        );
    }
}
