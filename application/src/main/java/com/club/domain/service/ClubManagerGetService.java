package com.club.domain.service;

import com.club.domain.entity.Club;
import com.club.domain.entity.ClubManager;
import com.club.domain.repository.ClubMangerRepository;
import com.club.exception.ClubManagerNotFoundException;
import com.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubManagerGetService {

    private final ClubMangerRepository clubMangerRepository;

    public ClubManager find(Club club, User manager) {
        return clubMangerRepository.findByClubAndManager(club, manager)
                .orElseThrow(ClubManagerNotFoundException::new);
    }

    public ClubManager findByUserId(Club club, User user) {
        return clubMangerRepository.findByClubAndManager(club, user)
                .orElseThrow(ClubManagerNotFoundException::new);
    }

    public ClubManager findByUserId(Club club, long userId) {
        return clubMangerRepository.findByClubAndUserId(club, userId)
                .orElseThrow(ClubManagerNotFoundException::new);
    }

    public List<ClubManager> readAllManagers(UUID clubId) {
        return clubMangerRepository.findAllByClubId(clubId);
    }
}
