package com.landing.application.dto.response;

import com.landing.domain.constant.DisplayMode;
import com.landing.domain.entity.Landing;

public class LandingResponseDTO {
    public record General(
            String subDomain,
            String siteName,
            String notionPageLink,
            String favicon,
            String image
    ) {
    }

    public record Style(
            String callToAction,
            String buttonColor,
            String textColor,
            DisplayMode displayMode
    ) {
    }

    public record All(
            String siteTitle,
            String notionPageLink,
            String favicon,
            String image,
            String callToAction,
            String buttonColor,
            String textColor,
            DisplayMode displayMode
    ) {
        public static All toAll(Landing landing, String parsedNotionPageLink) {

            return new All(
                    landing.getSiteName(),
                    parsedNotionPageLink,
                    landing.getFavicon(),
                    landing.getImage(),
                    landing.getCallToAction(),
                    landing.getButtonColor(),
                    landing.getTextColor(),
                    landing.getDisplayMode()
            );
        }
    }
}
