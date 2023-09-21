package org.pmu.domain.user.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-feign-client", url = "https://kapi.kakao.com/")
public interface KakaoFeignClient {
    @GetMapping("v1/user/access_token_info")
    KakaoAccessTokenInfo getKakaoAccessTokenInfo(@RequestHeader("Authorization") String accessToken);

    @GetMapping("v1/api/talk/profile")
    KakaoUserProfile getKakaoUserProfile(@RequestHeader("Authorization") String accessToken);
}
