package com.recruitment.exception;

import com.global.config.exception.ApplicationException;

import static com.recruitment.presentation.constant.ResponseMessage.MODIFIED_RECRUITMENT;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

public class ModifiedRecruitmentException extends ApplicationException {
    public ModifiedRecruitmentException() {
        super(NOT_ACCEPTABLE.value(), MODIFIED_RECRUITMENT.getMessage());
    }
}
