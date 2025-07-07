package com.form.exception;

import com.global.config.exception.ApplicationException;

import static com.form.presentation.constant.ResponseMessage.FORM_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class FormNotFoundException extends ApplicationException {
    public FormNotFoundException() {
        super(NOT_FOUND.value(), FORM_NOT_FOUND.getMessage());
    }
}
