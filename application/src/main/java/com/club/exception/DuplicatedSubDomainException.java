package com.club.exception;

import com.global.config.exception.ApplicationException;

import static com.club.presentation.constant.ResponseMessage.DUPLICATED_SUBDOMAIN;
import static org.springframework.http.HttpStatus.CONFLICT;

public class DuplicatedSubDomainException extends ApplicationException {
    public DuplicatedSubDomainException() {
        super(CONFLICT.value(), DUPLICATED_SUBDOMAIN.getMessage());
    }
}
