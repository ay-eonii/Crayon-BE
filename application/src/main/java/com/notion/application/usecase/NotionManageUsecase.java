package com.notion.application.usecase;

import java.util.Map;

public interface NotionManageUsecase {
	Map<String, Object> getPage(String pageId);
}
