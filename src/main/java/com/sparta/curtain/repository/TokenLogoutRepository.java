package com.sparta.curtain.repository;

import com.sparta.curtain.entity.TokenLogout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenLogoutRepository extends JpaRepository<TokenLogout, Long> {
    Optional<TokenLogout> findToken(String tokenValue);
}
