package com.example.identity_service.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),//500
    USER_EXISTED(1001,"User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002,"User not existed", HttpStatus.NOT_FOUND),
    INVALID_USERNAME(1003,"Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004,"Password must be at least 8 characters", HttpStatus.BAD_REQUEST),

    INVALID_KEY(1005,"Invalid message key", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006,"Unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007,"You do not have permission ",HttpStatus.FORBIDDEN)

            ;

    int code;
    String message;
    HttpStatusCode statusCode;



}
