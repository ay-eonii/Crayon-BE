package com.club.domain.service;

import com.club.domain.entity.Club;
import com.club.domain.repository.ClubMangerRepository;
import com.club.exception.ClubAccessDeniedException;
import com.recruitment.domain.entity.Process;
import com.recruitment.domain.entity.Recruitment;
import com.recruitment.domain.repository.RecruitmentRepository;
import com.recruitment.exception.RecruitmentNotFoundException;
import com.user.domain.entity.User;
import com.user.domain.repository.UserRepository;
import com.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubManagerAuthService {

    private final ClubMangerRepository clubMangerRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;

    public void checkAuthorization(UUID recruitmentId, User manager) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(RecruitmentNotFoundException::new);

        Club club = recruitment.getClub();
        checkAuthorization(club, manager);
    }

    public void checkAuthorization(Process process, User manager) {
        Recruitment recruitment = process.getRecruitment();

        if (recruitment == null) {
            throw new RecruitmentNotFoundException();
        }

        Club club = recruitment.getClub();

        checkAuthorization(club, manager);
    }

    public void checkAuthorization(UUID recruitmentId, long userId) {
        User manager = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(RecruitmentNotFoundException::new);

        Club club = recruitment.getClub();
        checkAuthorization(club, manager);
    }

    public void checkAuthorization(Club club, User manager) {
        if (clubMangerRepository.existsByClubAndManager(club, manager)) {
            return;
        }
        throw new ClubAccessDeniedException();
    }
}
