package org.pmu.domain.music.dto.response;

import org.pmu.domain.music.domain.Music;

public record MusicSaveResponseDto(
        Long musicId
) {
    public static MusicSaveResponseDto of(Music music) {
        return new MusicSaveResponseDto(music.getId());
    }
}
