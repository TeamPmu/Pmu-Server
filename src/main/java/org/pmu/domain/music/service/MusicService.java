package org.pmu.domain.music.service;

import lombok.RequiredArgsConstructor;
import org.pmu.domain.music.domain.Music;
import org.pmu.domain.music.dto.request.MusicSaveRequestDto;
import org.pmu.domain.music.dto.response.MusicSaveResponseDto;
import org.pmu.domain.music.repository.MusicRepository;
import org.pmu.domain.user.domain.User;
import org.pmu.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.pmu.domain.music.domain.Music.createMusic;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MusicService {
    private final UserRepository userRepository;
    private final MusicRepository musicRepository;

    @Transactional
    public MusicSaveResponseDto saveMusic(Long userId, MusicSaveRequestDto musicSaveRequestDto) {
        User findUser = userRepository.findByIdOrThrow(userId);
        Music music = createMusic(findUser, musicSaveRequestDto.coverImageUrl(), musicSaveRequestDto.genre(),
                musicSaveRequestDto.title(), musicSaveRequestDto.singer(), musicSaveRequestDto.youtubeUrl());
        Music savedMusic = musicRepository.save(music);
        return MusicSaveResponseDto.of(savedMusic);
    }
}
