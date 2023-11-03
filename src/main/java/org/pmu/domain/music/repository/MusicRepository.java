package org.pmu.domain.music.repository;

import org.pmu.domain.music.domain.Music;
import org.pmu.global.error.EntityNotFoundException;
import org.pmu.global.error.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
    Page<Music> findByUserId(Long userId, Pageable pageable);

    default Music findByIdOrThrow(Long musicId) {
        return findById(musicId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MUSIC_NOT_FOUND));
    }
}
