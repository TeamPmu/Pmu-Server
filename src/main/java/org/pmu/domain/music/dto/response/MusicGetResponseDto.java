package org.pmu.domain.music.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.pmu.domain.music.domain.Music;

@Builder(access = AccessLevel.PRIVATE)
public record MusicGetResponseDto(
        Long musicId,
        String coverImageUrl,
        String title,
        String singer
) {
    public static MusicGetResponseDto of(Music music) {
        return MusicGetResponseDto.builder()
                .musicId(music.getId())
                .coverImageUrl(music.getCoverImageUrl())
                .title(music.getTitle())
                .singer(music.getSinger())
                .build();
    }
}
