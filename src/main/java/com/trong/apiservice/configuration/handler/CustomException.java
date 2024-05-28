package com.trong.apiservice.configuration.handler;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {

    private HttpException httpException;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpException = new HttpException(errorCode);
    }

    public CustomException(ErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.httpException = new HttpException(errorCode, detailMessage);
    }
}
