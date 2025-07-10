package com.infra.redis.limiter;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LimitInfoTest {

	@DisplayName("만료 시각은 다음 날 자정이다.")
	@Test
	void getExpireAt_shouldReturnMidnightEpochForTomorrowUTC() {
		// given
		LimitInfo limitInfo = new LimitInfo();

		// when
		long expireAt = limitInfo.getExpireAt();

		// then
		LocalDateTime expectedDateTime = LocalDate.now(ZoneOffset.UTC)
			.plusDays(1)
			.atStartOfDay();

		long expectedEpoch = expectedDateTime.toEpochSecond(ZoneOffset.UTC);

		assertThat(expireAt).isEqualTo(expectedEpoch);
	}
}
