package com.landing.exception;

import static com.landing.presentation.constant.ResponseMessage.INVALID_FORMAT;

import com.global.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class InvalidFormatException extends ApplicationException {
    public InvalidFormatException() {
        super(HttpStatus.BAD_REQUEST.value(), INVALID_FORMAT.getMessage());
    }
}
