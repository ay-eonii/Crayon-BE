package com.application.exception;

import com.global.config.exception.ApplicationException;

import static com.application.presentation.constant.ResponseMessage.APPLICATION_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ApplicationNotFoundException extends ApplicationException {
    public ApplicationNotFoundException() {
        super(NOT_FOUND.value(), APPLICATION_NOT_FOUND.getMessage());
    }
}
