package com.recruitment.exception;

import com.recruitment.presentation.constant.ResponseMessage;
import com.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ProcessStepUnModifiableException extends ApplicationException {
    public ProcessStepUnModifiableException(ResponseMessage message) {
        super(BAD_REQUEST.value(), message.getMessage());
    }
}
