package com.trong.apiservice.configuration.handler;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SIGN_FAIL(101, "Sign failed"),
    PARAM_MISSING(102, "Params missing"),
    SYSTEM_ERROR(103, "System error"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    //Authentication message
    UNAUTHORIZED(401, "Unauthorized"),
    BAD_CREDENTIALS_EXCEPTION(401, "User info is invalid"),
    EXPIRED_JWT(401, "Token is expired");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
