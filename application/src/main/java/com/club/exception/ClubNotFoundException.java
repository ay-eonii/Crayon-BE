package com.club.exception;

import com.global.config.exception.ApplicationException;

import static com.club.presentation.constant.ResponseMessage.CLUB_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ClubNotFoundException extends ApplicationException {
    public ClubNotFoundException() {
        super(NOT_FOUND.value(), CLUB_NOT_FOUND.getMessage());
    }
}
