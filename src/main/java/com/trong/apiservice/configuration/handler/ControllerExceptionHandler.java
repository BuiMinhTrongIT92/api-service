package com.trong.apiservice.configuration.handler;

import com.trong.apiservice.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllException(Exception ex, WebRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("error_code", ErrorCode.INTERNAL_SERVER_ERROR.getCode());
        log.error("Handle all exception: " + ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAllResourceNotFoundException(NoResourceFoundException ex, WebRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("error_code", ErrorCode.NOT_FOUND.getCode());
        log.error("Handle ResourceNotFoundException exception: " + ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpException> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        HttpException httpException = new HttpException(ErrorCode.PARAM_MISSING, null != fieldError.getDefaultMessage() ? fieldError.getDefaultMessage() : ex.getMessage());
        log.error("Validation error: " + ex.getMessage());
        return new ResponseEntity<>(httpException, HttpStatus.OK);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<HttpException> handleCodeException(CustomException ex, WebRequest request) {
        if (ex.getHttpException() != null) {
            log.error("Handle custom exception: " + CommonUtils.objectToString(ex.getHttpException()));
            return new ResponseEntity<>(ex.getHttpException(), HttpStatus.OK);
        } else {
            log.error("Handle custom exception: " + CommonUtils.objectToString(ex.getMessage()));
        }
        return new ResponseEntity<>(new HttpException(ErrorCode.SYSTEM_ERROR, ex.getMessage()), HttpStatus.OK);
    }
}
