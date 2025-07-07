package com.infra.aws.exception;

import static com.infra.aws.constant.ResponseMessage.*;

import org.springframework.http.HttpStatus;

import com.global.config.exception.ApplicationException;

public class DistributeNotFoundException extends ApplicationException {
	public DistributeNotFoundException() {
		super(HttpStatus.NOT_FOUND.value(), DISTRIBUTE_NOT_FOUND.getMessage());
	}
}
