package com.application.exception;

import com.global.config.exception.ApplicationException;

import static com.application.presentation.constant.ResponseMessage.ACCESS_DENIED;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class AccessDeniedException extends ApplicationException {
    public AccessDeniedException() {
        super(FORBIDDEN.value(), ACCESS_DENIED.getMessage());
    }
}
