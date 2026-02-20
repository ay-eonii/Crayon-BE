package com.mail.exception;

import com.global.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

import static com.mail.presentation.constant.ResponseMessage.MAIL_STRATEGY_MISMATCH;

public class MailStrategyMismatchException extends ApplicationException {
    public MailStrategyMismatchException() {
        super(HttpStatus.BAD_REQUEST.value(), MAIL_STRATEGY_MISMATCH.getMessage());
    }
}
