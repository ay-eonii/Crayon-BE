package com.mail.exception;

import com.global.config.exception.ApplicationException;

import static com.mail.presentation.constant.ResponseMessage.MAIL_ALREADY_SENT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class MailAlreadySentException extends ApplicationException {
    public MailAlreadySentException() {
        super(BAD_REQUEST.value(), MAIL_ALREADY_SENT.getMessage());
    }
}
