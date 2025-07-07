package com.landing.exception;

import static com.landing.presentation.constant.ResponseMessage.LANDING_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.global.config.exception.ApplicationException;

public class LandingNotFoundException extends ApplicationException {
    public LandingNotFoundException() {
        super(NOT_FOUND.value(), LANDING_NOT_FOUND.getMessage());
    }
}
