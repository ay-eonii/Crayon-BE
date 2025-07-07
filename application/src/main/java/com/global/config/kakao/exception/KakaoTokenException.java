package com.global.config.kakao.exception;

import com.global.config.exception.ApplicationException;

import static com.global.config.kakao.presentation.constant.ResponseMessage.KAKAO_TOKEN_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class KakaoTokenException extends ApplicationException {
    public KakaoTokenException() {
        super(INTERNAL_SERVER_ERROR.value(),KAKAO_TOKEN_ERROR.getMessage());
    }
}
