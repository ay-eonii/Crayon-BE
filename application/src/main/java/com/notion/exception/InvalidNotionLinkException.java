package com.notion.exception;

import static com.notion.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.global.config.exception.ApplicationException;

public class InvalidNotionLinkException extends ApplicationException {
	public InvalidNotionLinkException() {
		super(BAD_REQUEST.value(), INVALID_LINK.getMessage());
	}
}
