package com.application.exception;

import com.global.config.exception.ApplicationException;

import static com.application.presentation.constant.ResponseMessage.QUESTION_REPLY_SIZE_MISMATCH;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class QuestionReplySizeMismatchException extends ApplicationException {
    public QuestionReplySizeMismatchException() {
        super(BAD_REQUEST.value(), QUESTION_REPLY_SIZE_MISMATCH.getMessage());
    }
}
