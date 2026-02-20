package com.mail.exception;

import com.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CreateScheduleException extends ApplicationException {
    public CreateScheduleException(String message) {
        super(BAD_REQUEST.value(), message);
    }
}
