package com.application.exception;

import static com.application.presentation.constant.ResponseMessage.ALREADY_APPLIED;
import static org.springframework.http.HttpStatus.CONFLICT;

import com.global.config.exception.ApplicationException;

public class AlreadyAppliedException extends ApplicationException {
    public AlreadyAppliedException() {
        super(CONFLICT.value(), ALREADY_APPLIED.getMessage());
    }
}
