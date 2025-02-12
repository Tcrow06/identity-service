package com.example.identity_service.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"uncategorized error"),
    USER_EXISTED(1001,"User existed"),
    INVALID_USERNAME(1003,"Username must be at least 3 characters"),
    INVALID_PASSWORD(1004,"Password must be at least 8 characters"),

    INVALID_KEY(9998,"Invalid message key"),
    USER_NOT_EXISTED(1002,"User not existed"),
    UNAUTHENTICATED(1006,"Unauthenticated")
    ;

    int code;
    String message;



}
