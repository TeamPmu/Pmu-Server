package org.pmu.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.pmu.domain.music.domain.Music;
import org.pmu.global.common.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@Table(name = "users")
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Builder.Default
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Music> musics = new ArrayList<>();
    @Column(updatable = false)
    private String platformId;
    @Column(updatable = false)
    private String profileImageUrl;
    @Column(updatable = false)
    private String nickname;
    private String refreshToken;

    public static User createUser(String platformId, String profileImageUrl, String nickname) {
        return User.builder()
                .platformId(platformId)
                .profileImageUrl(profileImageUrl)
                .nickname(nickname)
                .build();
    }

    public void addMusic(Music music) {
        musics.add(music);
    }

    public void removeMusic(Music music) {
        musics.remove(music);
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
