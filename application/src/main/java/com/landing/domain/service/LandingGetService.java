package com.landing.domain.service;

import com.club.domain.entity.Club;
import com.landing.domain.entity.Landing;
import com.landing.domain.repository.LandingRepository;
import com.landing.exception.LandingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandingGetService {
    
    private final LandingRepository landingRepository;

    public Landing find(Club club) {
        return landingRepository.findByClub(club).orElseThrow(LandingNotFoundException::new);
    }
}
