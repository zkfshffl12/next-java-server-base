package com.next.app.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final int code;
    private final String message;
    private final String path;
    
    public ErrorResponse(LocalDateTime timestamp, int status, String error, int code, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.code = code;
        this.message = message;
        this.path = path;
    }
    
    public static ErrorResponse of(ErrorCode errorCode, String path) {
        return new ErrorResponse(
                LocalDateTime.now(),
                errorCode.getCode() / 1000 * 100, // 에러 코드를 HTTP 상태 코드로 변환
                errorCode.name(),
                errorCode.getCode(),
                errorCode.getMessage(),
                path
        );
    }
    
    public static ErrorResponse of(ErrorCode errorCode, String message, String path) {
        return new ErrorResponse(
                LocalDateTime.now(),
                errorCode.getCode() / 1000 * 100,
                errorCode.name(),
                errorCode.getCode(),
                message,
                path
        );
    }
    
    // Getters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public int getStatus() {
        return status;
    }
    
    public String getError() {
        return error;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getPath() {
        return path;
    }
} 