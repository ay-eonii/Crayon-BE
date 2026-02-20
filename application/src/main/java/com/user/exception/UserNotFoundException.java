package com.user.exception;

import com.global.config.exception.ApplicationException;

import static com.user.presentation.constant.ResponseMessage.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException() {
        super(NOT_FOUND.value(), USER_NOT_FOUND.getMessage());
    }
}
