package com.application.exception;

import com.global.config.exception.ApplicationException;

import static com.application.presentation.constant.ResponseMessage.FAILED_BATCH_INSERT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class BatchInsertFailException extends ApplicationException {
    public BatchInsertFailException() {
        super(INTERNAL_SERVER_ERROR.value(), FAILED_BATCH_INSERT.getMessage());
    }
}
