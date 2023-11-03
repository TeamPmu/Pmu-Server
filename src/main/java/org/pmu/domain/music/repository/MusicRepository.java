package org.pmu.domain.music.repository;

import org.pmu.domain.music.domain.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
    Page<Music> findByUserId(Long userId, Pageable pageable);
}
