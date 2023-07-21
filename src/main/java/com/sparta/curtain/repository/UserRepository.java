package com.sparta.curtain.repository;

import com.sparta.curtain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    //인증이 된 유저만 이름으로 조회
    //Optional<User> findByEmailAndIsConfirmIsTrue(String email);
    // email 속성을 기준으로 User 엔티티를 조회하는 메서드
    Optional<User> findByEmail(String email);

    //Optional<User> findByKakaoId(Long kakaoId);

    Optional<User> findByEmailContaining(String keyword);
}
