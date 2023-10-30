package org.pmu.domain.user.repository;

import org.pmu.domain.user.domain.User;
import org.pmu.global.error.EntityNotFoundException;
import org.pmu.global.error.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByNickname(String nickname);

    boolean existsUserByPlatformId(String platformId);

    Optional<User> findUserByPlatformId(String platformId);

    default User findByIdOrThrow(Long userId) {
        return findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
