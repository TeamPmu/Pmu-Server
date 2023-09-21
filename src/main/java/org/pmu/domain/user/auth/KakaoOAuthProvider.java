package org.pmu.domain.user.auth;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pmu.global.error.ErrorCode;
import org.pmu.global.error.UnauthorizedException;
import org.springframework.stereotype.Component;

import static org.pmu.domain.user.auth.KakaoAccessToken.createKakaoAccessToken;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoOAuthProvider {
    private final KakaoFeignClient kakaoFeignClient;

    public String getKakaoPlatformId(String accessToken) {
        KakaoAccessToken kakaoAccessToken = createKakaoAccessToken(accessToken);
        String accessTokenWithTokenType = kakaoAccessToken.getAccessTokenWithTokenType();
        KakaoAccessTokenInfo kakaoAccessTokenInfo = getKakaoAccessTokenInfo(accessTokenWithTokenType);
        return String.valueOf(kakaoAccessTokenInfo.getId());
    }

    public String getKakaoUserProfileImageUrl(String accessToken) {
        KakaoAccessToken kakaoAccessToken = createKakaoAccessToken(accessToken);
        String accessTokenWithTokenType = kakaoAccessToken.getAccessTokenWithTokenType();
        KakaoUserProfile kakaoUserProfile = getKakaoUserProfile(accessTokenWithTokenType);
        return kakaoUserProfile.getProfileImageURL();
    }

    private KakaoAccessTokenInfo getKakaoAccessTokenInfo(String accessTokenWithTokenType) {
        try {
            return kakaoFeignClient.getKakaoAccessTokenInfo(accessTokenWithTokenType);
        } catch (FeignException e) {
            log.error("Feign Exception: ", e);
            throw new UnauthorizedException(ErrorCode.INVALID_KAKAO_ACCESS_TOKEN);
        }
    }

    private KakaoUserProfile getKakaoUserProfile(String accessTokenWithTokenType) {
        try {
            return kakaoFeignClient.getKakaoUserProfile(accessTokenWithTokenType);
        } catch (FeignException e) {
            log.error("Feign Exception: ", e);
            throw new UnauthorizedException(ErrorCode.INVALID_KAKAO_ACCESS_TOKEN);
        }
    }
}
