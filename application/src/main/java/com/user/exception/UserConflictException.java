package com.user.exception;

import com.global.config.exception.ApplicationException;

import static com.user.presentation.constant.ResponseMessage.DUPLICATE_USERNAME;
import static org.springframework.http.HttpStatus.CONFLICT;

public class UserConflictException  extends ApplicationException {
    public UserConflictException() {
        super(CONFLICT.value(), DUPLICATE_USERNAME.getMessage());
    }
}
