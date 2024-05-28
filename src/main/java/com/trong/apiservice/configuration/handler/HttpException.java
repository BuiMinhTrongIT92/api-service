package com.trong.apiservice.configuration.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class HttpException {
    private boolean status;
    @JsonProperty("error_code")
    private int errorCode;
    @JsonProperty("error_message")
    private String errorMessage;

    public HttpException(int errorCode) {
        this.errorCode = errorCode;
    }

    public HttpException(ErrorCode errorCode) {
        this.status = false;
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorCode.getMessage();
    }

    public HttpException(ErrorCode errorCode, String messageDetail) {
        this.status = false;
        this.errorCode = errorCode.getCode();
        this.errorMessage = String.format("%s - {%s}", errorCode.getMessage(), messageDetail);
    }

    public HttpException(int errorCode, String messageDetail) {
        this.status = false;
        this.errorCode = errorCode;
        this.errorMessage = messageDetail;
    }
}
