package com.club.domain.service;

import com.club.domain.entity.Club;
import com.club.domain.entity.ClubManager;
import com.club.domain.repository.ClubMangerRepository;
import com.club.domain.repository.ClubRepository;
import com.club.exception.ClubAccessDeniedException;
import com.club.exception.ClubNotFoundException;
import com.club.exception.DuplicatedSubDomainException;
import com.user.domain.entity.User;
import com.global.common.util.SubdomainFormatter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubValidateService {

    private final ClubRepository clubRepository;
    private final ClubMangerRepository clubMangerRepository;

    public Club checkAuthority(String clubId, User manager) {
        return checkAuthority(UUID.fromString(clubId), manager);
    }

    public Club checkOwnerAuthority(UUID clubId, User manager) {
        Club club = clubRepository.findByIdAndDeletedAtIsNull(clubId)
                .orElseThrow(ClubNotFoundException::new);

        ClubManager clubManager = clubMangerRepository.findByClubAndManager(club, manager)
                .orElseThrow(ClubAccessDeniedException::new);

        if (clubManager.isOwner()) {
            return club;
        }
        throw new ClubAccessDeniedException();
    }

    public Club checkAuthority(UUID clubId, User manager) {
        Club club = clubRepository.findByIdAndDeletedAtIsNull(clubId)
                .orElseThrow(ClubNotFoundException::new);

        if (clubMangerRepository.existsByClubAndManager(club, manager)) {
            return club;
        }
        throw new ClubAccessDeniedException();
    }

    public void checkDuplicatedSubDomain(String subdomain) {
        if (clubRepository.existsBySubDomain(SubdomainFormatter.formatPrefix(subdomain))) {
            throw new DuplicatedSubDomainException();
        }
    }
}
