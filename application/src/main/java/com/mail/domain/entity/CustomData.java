package com.mail.domain.entity;

import com.application.domain.entity.Application;
import com.application.domain.entity.Interview;
import com.application.domain.entity.enums.Status;
import com.application.domain.repository.dto.ApplicationWithStatus;
import com.mail.domain.entity.enums.CustomType;
import com.global.common.util.DateFormatter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomData {

    private final String email;
    private final Map<CustomType, String> data;
    private final boolean isPass;

    public static CustomData of(ApplicationWithStatus applicationWithStatus) {
        Application application = applicationWithStatus.application();
        Status status = applicationWithStatus.status();
        Interview interview = application.getInterview();
        Map<CustomType, String> data = Map.of(
                CustomType.USER_NAME, application.getUserName(),
                CustomType.INTERVIEW_DATE, getInterviewDate(interview),
                CustomType.INTERVIEW_PLACE, getInterviewPlace(interview)
        );
        return new CustomData(application.getEmail(), data, status.isPass());
    }

    private static String getInterviewDate(Interview interview) {
        if (interview == null) {
            return "";
        }
        String date = interview.getDate();
        return DateFormatter.formatMailDate(date);
    }

    private static String getInterviewPlace(Interview interview) {
        if (interview == null) {
            return "";
        }
        return interview.getPlace();
    }
}
