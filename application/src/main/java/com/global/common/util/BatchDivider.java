package com.global.common.util;

import java.util.ArrayList;
import java.util.List;

public class BatchDivider {

	private BatchDivider() {
	}

	public static <T> List<List<T>> divide(List<T> items, int batchSize) {
		List<List<T>> batches = new ArrayList<>();
		for (int i = 0; i < items.size(); i += batchSize) {
			batches.add(items.subList(i, Math.min(i + batchSize, items.size())));
		}
		return batches;
	}
}
