package com.ceos21.spring_knowledgeIn_21st.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // CommonError
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),

    // PostError
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not exists"),

    // UserError
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not exists"),
    ;

    private final HttpStatus status;
    private final String message;
}