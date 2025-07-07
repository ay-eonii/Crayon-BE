package com.image.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.image.ImageUploader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

	private final ImageUploader imageUploader;

	public List<String> save(List<MultipartFile> images) {
		List<String> urls = new ArrayList<>();
		for (MultipartFile image : images) {
			String fileName = getFileName(image);
			String key = "image/" + fileName;
			String url = imageUploader.save(image, key);
			urls.add(url);
		}
		return urls;
	}

	private String getFileName(MultipartFile image) {
		String originalFilename = image.getOriginalFilename();
		int index = originalFilename.lastIndexOf(".");
		String ext = originalFilename.substring(index + 1);

		return UUID.randomUUID() + "." + ext;
	}
}
