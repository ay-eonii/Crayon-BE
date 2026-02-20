package com.global.config.jwt.exception;

import com.global.config.exception.ApplicationException;
import static com.global.config.jwt.presentation.constant.ResponseMessage.EXPIRED_TOKEN;
import static org.springframework.http.HttpStatus.FORBIDDEN;
public class ExpiredTokenException extends ApplicationException {
    public ExpiredTokenException() {
        super(FORBIDDEN.value(), EXPIRED_TOKEN.getMessage());
    }
}
