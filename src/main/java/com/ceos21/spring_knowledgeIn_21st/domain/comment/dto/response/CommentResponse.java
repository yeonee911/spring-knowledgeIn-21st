package com.ceos21.spring_knowledgeIn_21st.domain.comment.dto.response;

import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.Comment;
import com.ceos21.spring_knowledgeIn_21st.domain.user.dto.reponse.UserSummaryResponse;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UserSummaryResponse user
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                new UserSummaryResponse(
                        comment.getUser().getId(),
                        comment.getUser().getName(),
                        comment.getUser().getEmail()
                )
        );
    }
}
