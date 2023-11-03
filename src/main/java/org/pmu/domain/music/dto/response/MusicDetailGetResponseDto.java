package org.pmu.domain.music.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.pmu.domain.music.domain.Music;

@Builder(access = AccessLevel.PRIVATE)
public record MusicDetailGetResponseDto(
        Long musicId,
        String coverImageUrl,
        String title,
        String singer,
        String youtubeUrl
) {
    public static MusicDetailGetResponseDto of(Music music) {
        return MusicDetailGetResponseDto.builder()
                .musicId(music.getId())
                .coverImageUrl(music.getCoverImageUrl())
                .title(music.getTitle())
                .singer(music.getSinger())
                .youtubeUrl(music.getYoutubeUrl())
                .build();
    }
}
