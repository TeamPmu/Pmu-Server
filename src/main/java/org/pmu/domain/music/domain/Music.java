package org.pmu.domain.music.domain;

import jakarta.persistence.*;
import lombok.*;
import org.pmu.domain.user.domain.User;
import org.pmu.global.common.BaseTimeEntity;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Music extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(updatable = false)
    private String coverImageUrl;
    @Column(updatable = false)
    private String title;
    @Column(updatable = false)
    private String singer;
    @Column(updatable = false)
    private String youtubeUrl;

    public static Music createMusic(final User user, final String coverImageUrl, final String title, final String singer, final String youtubeUrl) {
        Music music = Music.builder()
                .coverImageUrl(coverImageUrl)
                .title(title)
                .singer(singer)
                .youtubeUrl(youtubeUrl)
                .build();
        music.changeUser(user);
        return music;
    }

    public void changeUser(User user) {
        if (this.user != null) {
            this.user.removeMusic(this);
        }
        this.user = user;
        user.addMusic(this);
    }
}
