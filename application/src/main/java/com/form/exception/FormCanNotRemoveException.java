package com.form.exception;

import com.global.config.exception.ApplicationException;

import static com.form.presentation.constant.ResponseMessage.FORM_CAN_NOT_REMOVE;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

public class FormCanNotRemoveException extends ApplicationException {
    public FormCanNotRemoveException() {
        super(NOT_ACCEPTABLE.value(), FORM_CAN_NOT_REMOVE.getMessage());
    }
}
