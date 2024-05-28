package com.trong.apiservice.configuration.handler;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SIGN_FAIL(101, "Sign failed"),
    PARAM_MISSING(102, "Params missing"),
    SYSTEM_ERROR(103, "System error"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
