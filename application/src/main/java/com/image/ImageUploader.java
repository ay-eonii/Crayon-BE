package com.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {
	String save(MultipartFile multipartFile, String key);

}
