package com.notion.application.usecase;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.notion.service.NotionGetServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotionManageUsecaseImpl implements NotionManageUsecase {
	private final NotionGetServiceImpl notionGetService;

	@Override
	public Map<String, Object> getPage(String pageId) {
		return notionGetService.getPageChunk(pageId);
	}
}
