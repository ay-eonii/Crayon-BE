package com.club.exception;

import com.global.config.exception.ApplicationException;

import static com.club.presentation.constant.ResponseMessage.DUPLICATED_PARTICIPATION;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class DuplicatedParticipationException extends ApplicationException {
    public DuplicatedParticipationException() {
        super(NOT_FOUND.value(), DUPLICATED_PARTICIPATION.getMessage());
    }
}
