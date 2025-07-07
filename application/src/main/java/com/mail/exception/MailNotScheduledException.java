package com.mail.exception;

import com.global.config.exception.ApplicationException;

import static com.mail.presentation.constant.ResponseMessage.MAIL_NOT_SCHEDULED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class MailNotScheduledException extends ApplicationException {
    public MailNotScheduledException() {
        super(NOT_FOUND.value(), MAIL_NOT_SCHEDULED.getMessage());
    }
}
