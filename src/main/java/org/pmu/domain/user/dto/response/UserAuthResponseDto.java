package org.pmu.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.pmu.domain.user.domain.User;
import org.pmu.global.config.jwt.Token;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class UserAuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String profileImageUrl;
    private String nickname;

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
