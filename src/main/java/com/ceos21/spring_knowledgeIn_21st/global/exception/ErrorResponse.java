package com.ceos21.spring_knowledgeIn_21st.global.exception;

public record ErrorResponse(String errorCodeName, String errorMessage) {
    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.name(), errorCode.getMessage());
    }
}