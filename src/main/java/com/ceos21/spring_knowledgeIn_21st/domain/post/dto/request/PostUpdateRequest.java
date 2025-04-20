package com.ceos21.spring_knowledgeIn_21st.domain.post.dto.request;

import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import com.ceos21.spring_knowledgeIn_21st.domain.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PostUpdateRequest(
        
        String title,
        String content
){}
