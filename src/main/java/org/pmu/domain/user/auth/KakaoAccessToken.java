package org.pmu.domain.user.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class KakaoAccessToken {
    private static final String TOKEN_TYPE = "Bearer ";
    private String accessToken;

    public static KakaoAccessToken createKakaoAccessToken(String accessToken) {
        return new KakaoAccessToken(accessToken);
    }

    public String getAccessTokenWithTokenType() {
        return TOKEN_TYPE + accessToken;
    }
}
