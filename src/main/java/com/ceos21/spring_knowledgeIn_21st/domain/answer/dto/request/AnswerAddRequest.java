package com.ceos21.spring_knowledgeIn_21st.domain.answer.dto.request;

import com.ceos21.spring_knowledgeIn_21st.domain.answer.domain.Answer;
import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import com.ceos21.spring_knowledgeIn_21st.domain.user.domain.User;
import jakarta.validation.constraints.NotBlank;

public record AnswerAddRequest (
    @NotBlank
    String content
){
    public Answer toEntity(User user, Post post) {
        return Answer.builder()
                .content(content)
                .user(user)
                .post(post)
                .build();
    }
}
