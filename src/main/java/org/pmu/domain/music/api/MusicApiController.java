package org.pmu.domain.music.api;

import lombok.RequiredArgsConstructor;
import org.pmu.domain.music.dto.request.MusicSaveRequestDto;
import org.pmu.domain.music.dto.response.MusicSaveResponseDto;
import org.pmu.domain.music.service.MusicService;
import org.pmu.global.common.BaseResponse;
import org.pmu.global.common.SuccessCode;
import org.pmu.global.common.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/api/music")
@Controller
public class MusicApiController {
    private final MusicService musicService;

    @PostMapping
    public ResponseEntity<BaseResponse<?>> saveMusic(@UserId final Long userId,
                                                     @RequestBody final MusicSaveRequestDto musicSaveRequestDto) {
        final MusicSaveResponseDto musicSaveResponseDto = musicService.saveMusic(userId, musicSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.of(SuccessCode.CREATED, musicSaveResponseDto));
    }
}
