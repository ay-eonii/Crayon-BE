package com.infra.aws.exception;

import static org.springframework.http.HttpStatus.*;

import com.global.config.exception.ApplicationException;

public class FileNotFoundException extends ApplicationException {
	public FileNotFoundException() {
		super(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase());
	}
}
