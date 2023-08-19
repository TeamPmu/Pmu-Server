package org.pmu.domain.user.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KakaoAccessTokenInfo {
    private Long id;
}
