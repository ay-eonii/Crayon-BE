package com.user.domain.entity;

import com.global.common.entity.BaseEntity;
import com.global.config.jwt.exception.InvalidTokenException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String email;

    @Column(length = 13)
    private String tel;

    private String refreshToken;

    private LocalDateTime deletedAt;

    public User(String name, String email, String tel) {
        this.name = name;
        this.email = email;
        this.tel = tel;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void checkRefreshToken(String refreshToken) {
        if (!this.refreshToken.equals(refreshToken)) {
            throw new InvalidTokenException();
        }
    }
}
