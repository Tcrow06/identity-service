package com.example.identity_service.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"uncategorized error"),
    USER_EXISTED(1001,"User existed"),
    INVALID_USERNAME(1003,"Username must be at least 3 characters"),
    INVALID_PASSWORD(1003,"Password must be at least 8 characters"),

    INVALID_KEY(9998,"Invalid message key"),
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    private int code;
    private String message;


    public int getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }


}
