package com.landing.domain.service;

import com.club.domain.entity.Club;
import com.club.domain.service.ClubUpdateService;
import com.landing.application.dto.request.LandingRequestDTO.General;
import com.landing.application.dto.request.LandingRequestDTO.Style;
import com.landing.domain.entity.Landing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandingUpdateService {
    
    private final ClubUpdateService clubUpdateService;

    public void update(Landing landing, Style dto) {
        landing.updateStyle(dto);
    }

    public void update(Landing landing, Club club, General dto) {
        landing.updateGeneral(dto);
        clubUpdateService.update(club, dto);
    }
}
