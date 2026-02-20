package com.club.exception;

import com.global.config.exception.ApplicationException;

import static com.club.presentation.constant.ResponseMessage.CLUB_MANAGER_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ClubManagerNotFoundException extends ApplicationException {
    public ClubManagerNotFoundException() {
        super(NOT_FOUND.value(), CLUB_MANAGER_NOT_FOUND.getMessage());
    }
}
