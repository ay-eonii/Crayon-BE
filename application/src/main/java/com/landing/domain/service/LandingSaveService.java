package com.landing.domain.service;

import com.club.domain.entity.Club;
import com.landing.domain.entity.Landing;
import com.landing.domain.repository.LandingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandingSaveService {
    
    private final LandingRepository landingRepository;

    public Landing save(Club club) {
        return landingRepository.save(new Landing(club));
    }
}
