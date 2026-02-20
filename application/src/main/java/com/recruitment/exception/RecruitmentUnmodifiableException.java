package com.recruitment.exception;

import com.global.config.exception.ApplicationException;

import static com.recruitment.presentation.constant.ResponseMessage.RECRUITMENT_CANNOT_UPDATE;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

public class RecruitmentUnmodifiableException extends ApplicationException {
    public RecruitmentUnmodifiableException() {
        super(NOT_ACCEPTABLE.value(), RECRUITMENT_CANNOT_UPDATE.getMessage());
    }
}
