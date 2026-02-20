package com.recruitment.exception;

import com.global.config.exception.ApplicationException;

import static com.recruitment.presentation.constant.ResponseMessage.RECRUITMENT_DELETED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class RecruitmentDeletedException extends ApplicationException {
    public RecruitmentDeletedException() {
        super(NOT_FOUND.value(), RECRUITMENT_DELETED.getMessage());
    }
}
