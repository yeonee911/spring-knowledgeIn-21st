package com.ceos21.spring_knowledgeIn_21st.domain.comment.dto.request;

import com.ceos21.spring_knowledgeIn_21st.domain.answer.domain.Answer;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.BaseComment;
import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import com.ceos21.spring_knowledgeIn_21st.domain.user.domain.User;
import jakarta.validation.constraints.NotBlank;

public record CommentAddRequest (
        @NotBlank
        String content
){
    public BaseComment toEntity(Answer answer, Post post, User user) {
        return BaseComment.builder()
                .content(content)
                .answer(answer)
                .post(post)
                .user(user)
                .build();
    }
}