package com.recruitment.exception;

import com.global.config.exception.ApplicationException;

import static com.recruitment.presentation.constant.ResponseMessage.RECRUITMENT_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class RecruitmentNotFoundException extends ApplicationException {
    public RecruitmentNotFoundException() {
        super(NOT_FOUND.value(), RECRUITMENT_NOT_FOUND.getMessage());
    }
}
