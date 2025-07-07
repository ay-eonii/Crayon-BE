package com.form.exception;

import com.global.config.exception.ApplicationException;

import static com.form.presentation.constant.ResponseMessage.FORM_UNMODIFIABLE;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

public class FormUnmodifiableException extends ApplicationException {
    public FormUnmodifiableException() {
        super(NOT_ACCEPTABLE.value(), FORM_UNMODIFIABLE.getMessage());
    }
}
