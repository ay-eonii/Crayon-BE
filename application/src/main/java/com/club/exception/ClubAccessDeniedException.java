package com.club.exception;

import com.global.config.exception.ApplicationException;

import static com.club.presentation.constant.ResponseMessage.ACCESS_DENIED;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class ClubAccessDeniedException extends ApplicationException {
    public ClubAccessDeniedException() {
        super(FORBIDDEN.value(), ACCESS_DENIED.getMessage());
    }
}
