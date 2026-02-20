package com.mail.exception;

import com.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class LambdaInvokeException extends ApplicationException {
    public LambdaInvokeException(String message) {
        super(INTERNAL_SERVER_ERROR.value(), message);
    }
}
