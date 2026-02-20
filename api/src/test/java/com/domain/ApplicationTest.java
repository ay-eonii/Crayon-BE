package com.domain;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.domain.fixture.CustomRepository;

@ActiveProfiles("test")
@SpringBootTest(
	classes = com.api.ApiApplication.class
)
public class ApplicationTest {

	@Autowired
	CustomRepository customRepository;

	@AfterEach
	void tearDown() {
		customRepository.clearAndReset();
	}
}
