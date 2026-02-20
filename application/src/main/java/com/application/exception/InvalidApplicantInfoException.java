package com.application.exception;

import com.global.config.exception.ApplicationException;

import static com.application.presentation.constant.ResponseMessage.INVALID_APPLICANT_INFO;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidApplicantInfoException extends ApplicationException {
    public InvalidApplicantInfoException() {
        super(BAD_REQUEST.value(), INVALID_APPLICANT_INFO.getMessage());
    }
}
