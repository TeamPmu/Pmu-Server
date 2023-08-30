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
        User user = User.createUser(platformId, null, userSignUpRequestDto.getNickname());
        User savedUser = userRepository.save(user);
        Token issuedToken = jwtProvider.issueToken(savedUser.getId());
        updateRefreshToken(savedUser, issuedToken.getRefreshToken());
        return UserAuthResponseDto.of(issuedToken, savedUser);
    }

    public Token reissue(String refreshToken) {
        jwtProvider.validateRefreshToken(refreshToken);
        Long userId = jwtProvider.getSubject(refreshToken);
        User findUser = getUser(userId);
        jwtProvider.equalsRefreshToken(refreshToken, findUser.getRefreshToken());
        Token issuedToken = jwtProvider.issueToken(userId);
        updateRefreshToken(findUser, issuedToken.getRefreshToken());
        return issuedToken;
    }

    public void signOut(Long userId) {
        User findUser = getUser(userId);
        deleteRefreshToken(findUser);
    }

    public void withdraw(Long userId) {
        userRepository.deleteById(userId);
    }

    private User getUser(String platformId) {
        return userRepository.findUserByPlatformId(platformId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private void validateDuplicateNickname(String nickname) {
        if (userRepository.existsUserByNickname(nickname)) {
            throw new ConflictException(ErrorCode.DUPLICATE_NICKNAME);
        }
    }

    private void validateDuplicateUser(String platformId) {
        if (userRepository.existsUserByPlatformId(platformId)) {
            throw new ConflictException(ErrorCode.DUPLICATE_USER);
        }
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private void updateRefreshToken(User user, String refreshToken) {
        user.updateRefreshToken(refreshToken);
    }

    private void deleteRefreshToken(User findUser) {
        findUser.updateRefreshToken(null);
    }
}
