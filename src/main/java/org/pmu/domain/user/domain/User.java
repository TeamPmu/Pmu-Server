package org.pmu.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String platformId;
    private String profileImageUrl;
    private String nickname;
    private String refreshToken;

    public static User createUser(String platformId, String profileImageUrl, String nickname) {
        return User.builder()
                .platformId(platformId)
                .profileImageUrl(profileImageUrl)
                .nickname(nickname)
                .build();
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
