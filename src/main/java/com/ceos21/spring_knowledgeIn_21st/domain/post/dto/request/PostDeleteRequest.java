package com.ceos21.spring_knowledgeIn_21st.domain.post.dto.request;

import lombok.Getter;

@Getter
public class PostDeleteRequest {
    private Long userId;
    public PostDeleteRequest(Long postId, Long userId) {
        this.userId = userId;
    }
}
