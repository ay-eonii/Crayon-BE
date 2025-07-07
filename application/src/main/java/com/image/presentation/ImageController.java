package com.image.presentation;

import static com.image.presentation.constant.ResponseMessage.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.image.service.ImageService;
import com.global.common.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "IMAGE")
@RestController("/image")
@RequiredArgsConstructor
public class ImageController {
	private final ImageService imageService;

	@PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "[Image] 이미지 업로드")
	public ResponseDto<List<String>> returnImage(@RequestPart("images") List<MultipartFile> images) {
		return ResponseDto.of(HttpStatus.OK.value(), IMAGE_SAVE_SUCCESS.getMessage(), imageService.save(images));
	}
}
