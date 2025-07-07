package com.application.exception;

import com.global.config.exception.ApplicationException;

import static com.application.presentation.constant.ResponseMessage.APPLICATION_REPLY_SIZE_MISMATCH;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ApplicationReplySizeMismatchException extends ApplicationException {
    public ApplicationReplySizeMismatchException() {
        super(BAD_REQUEST.value(), APPLICATION_REPLY_SIZE_MISMATCH.getMessage());
    }
}
