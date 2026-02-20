package com.infra.aws.exception;

import static com.image.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.global.config.exception.ApplicationException;

public class ImageSaveFailureException extends ApplicationException {
	public ImageSaveFailureException() {
		super(INTERNAL_SERVER_ERROR.value(), IMAGE_SAVE_FAILURE.getMessage());
	}
}
