package com.spatra.curtain.repository;

import com.spatra.curtain.entity.TokenLogout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenLogoutRepository extends JpaRepository<TokenLogout, Long> {
    Optional<TokenLogout> findToken(String tokenValue);
}
