package org.pmu.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.pmu.domain.user.domain.User;
import org.pmu.global.config.jwt.Token;

@Builder(access = AccessLevel.PRIVATE)
public record UserAuthResponseDto(
        String accessToken,
        String refreshToken,
        Long userId,
        String profileImageUrl,
        String nickname
) {
    public static UserAuthResponseDto of(Token token, User user) {
        return UserAuthResponseDto.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .userId(user.getId())
                .profileImageUrl(user.getProfileImageUrl())
                .nickname(user.getNickname())
                .build();
    }
}