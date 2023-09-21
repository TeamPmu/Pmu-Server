package org.pmu.domain.user.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KakaoUserProfile {
    private String nickName;
    private String profileImageURL;
    private String thumbnailURL;
}
