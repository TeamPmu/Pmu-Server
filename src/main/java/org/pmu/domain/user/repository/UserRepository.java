package org.pmu.domain.user.repository;

import org.pmu.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByNickname(String nickname);

    List<User> findUsersByPlatformId(String platformId);

    Optional<User> findUserByPlatformId(String platformId);
}
