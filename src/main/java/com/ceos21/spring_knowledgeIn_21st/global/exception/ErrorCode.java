package com.ceos21.spring_knowledgeIn_21st.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();
    HttpStatus getStatus();
    String getMessage();
}