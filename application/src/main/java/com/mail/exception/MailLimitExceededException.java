package com.mail.exception;

import com.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

public class MailLimitExceededException extends ApplicationException {
    public MailLimitExceededException(String message) {
        super(TOO_MANY_REQUESTS.value(), message);
    }
}
