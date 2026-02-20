package com.application.exception;

import com.global.config.exception.ApplicationException;

import static com.application.presentation.constant.ResponseMessage.INVALID_DATA_TYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidDataType extends ApplicationException {
    public InvalidDataType() {
        super(BAD_REQUEST.value(), INVALID_DATA_TYPE.getMessage());
    }
}
