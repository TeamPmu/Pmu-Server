package org.pmu.domain.user.repository;

import org.pmu.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByNickname(String nickname);

    Optional<User> findUserByPlatformId(String platformId);
}
