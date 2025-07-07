package com.club.domain.repository;

import com.club.domain.entity.Club;
import com.club.domain.entity.ClubManager;
import com.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClubMangerRepository extends JpaRepository<ClubManager, Long> {

    Optional<ClubManager> findByClubAndManager(Club club, User manager);

    boolean existsByClubAndManager(Club club, User manager);

    @Query("SELECT cm.club FROM ClubManager cm WHERE cm.manager = :manager")
    List<Club> findAllMyClubs(User manager);

    List<ClubManager> findAllByClubId(UUID clubId);

    @Query("SELECT cm FROM ClubManager cm WHERE cm.club = :club AND cm.manager.id = :userId")
    Optional<ClubManager> findByClubAndUserId(Club club, long userId);

    @Modifying
    @Query("DELETE FROM ClubManager cm WHERE cm.club.id = :clubId AND cm.manager.id IN :userIds")
    void deleteAllByClubIdAndIds(UUID clubId, List<Long> userIds);
}
