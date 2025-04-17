package com.ceos21.spring_knowledgeIn_21st.domain.user.dto.request;

public record LoginRequest (
        String email,
        String password
){}
