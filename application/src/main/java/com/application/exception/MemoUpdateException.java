package com.application.exception;

import com.global.config.exception.ApplicationException;

import static com.application.presentation.constant.ResponseMessage.CANNOT_MEMO_UPDATE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class MemoUpdateException extends ApplicationException {
    public MemoUpdateException() {
        super(NOT_FOUND.value(), CANNOT_MEMO_UPDATE.getMessage());
    }
}
