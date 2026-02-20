package com.recruitment.exception;

import com.global.config.exception.ApplicationException;

import static com.recruitment.presentation.constant.ResponseMessage.EMPTY_PROCESS;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class ProcessEmptyException extends ApplicationException {
    public ProcessEmptyException() {
        super(INTERNAL_SERVER_ERROR.value(), EMPTY_PROCESS.getMessage());
    }
}
