package com.application.exception;

import com.global.config.exception.ApplicationException;

import static com.application.presentation.constant.ResponseMessage.INVALID_DATE_FORMAT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidDateFormat extends ApplicationException {
    public InvalidDateFormat() {
        super(BAD_REQUEST.value(), INVALID_DATE_FORMAT.getMessage());
    }
}
