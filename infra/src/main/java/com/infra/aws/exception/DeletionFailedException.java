package com.infra.aws.exception;

import org.springframework.http.HttpStatus;

import com.global.config.exception.ApplicationException;

public class DeletionFailedException extends ApplicationException {
	public DeletionFailedException(Exception e) {
		super(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
}
