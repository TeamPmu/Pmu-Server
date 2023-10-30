package org.pmu.domain.music.dto.request;

public record MusicSaveRequestDto(
        String coverImageUrl,
        String genre,
        String title,
        String singer,
        String youtubeUrl
) {
}
