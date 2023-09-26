package org.pmu.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@DynamicInsert
@DynamicUpdate
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

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
