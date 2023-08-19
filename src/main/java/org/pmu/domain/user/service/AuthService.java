package org.pmu.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.pmu.domain.user.auth.KakaoOAuthProvider;
import org.pmu.domain.user.domain.User;
import org.pmu.domain.user.dto.request.UserSignUpRequestDto;
import org.pmu.domain.user.dto.response.UserAuthResponseDto;
import org.pmu.domain.user.repository.UserRepository;
import org.pmu.global.config.jwt.JwtProvider;
import org.pmu.global.config.jwt.Token;
import org.pmu.global.error.ConflictException;
import org.pmu.global.error.EntityNotFoundException;
import org.pmu.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final KakaoOAuthProvider kakaoOAuthProvider;

    public UserAuthResponseDto signIn(String token) {
        String platformId = kakaoOAuthProvider.getKakaoPlatformId(token);
        User findUser = getUser(platformId);
        Token issuedToken = jwtProvider.issueToken(findUser.getId());
        updateRefreshToken(findUser, issuedToken.getRefreshToken());
        return UserAuthResponseDto.of(issuedToken, findUser);
    }

    public UserAuthResponseDto signUp(String token, UserSignUpRequestDto userSignUpRequestDto) {
        validateDuplicateNickname(userSignUpRequestDto.getNickname());
        String platformId = kakaoOAuthProvider.getKakaoPlatformId(token);
        validateDuplicateUser(platformId);
        User savedUser = User.createUser(platformId, null, userSignUpRequestDto.getNickname());
        Token issuedToken = jwtProvider.issueToken(savedUser.getId());
        updateRefreshToken(savedUser, issuedToken.getRefreshToken());
        return UserAuthResponseDto.of(issuedToken, savedUser);
    }

    private User getUser(String platformId) {
        return userRepository.findUserByPlatformId(platformId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private void validateDuplicateNickname(String nickname) {
        List<User> findUsers = userRepository.findUsersByNickname(nickname);
        if (!findUsers.isEmpty()) {
            throw new ConflictException(ErrorCode.DUPLICATE_NICKNAME);
        }
    }

    private void validateDuplicateUser(String platformId) {
        List<User> findUsers = userRepository.findUsersByPlatformId(platformId);
        if (!findUsers.isEmpty()) {
            throw new ConflictException(ErrorCode.DUPLICATE_USER);
        }
    }

    private void updateRefreshToken(User user, String refreshToken) {
        user.updateRefreshToken(refreshToken);
    }
}
