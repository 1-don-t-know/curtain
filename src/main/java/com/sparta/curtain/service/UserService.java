package com.sparta.curtain.service;

import com.sparta.curtain.dto.ProfileResponseDto;
import com.sparta.curtain.entity.User;
import com.sparta.curtain.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;

    // 프로필 조회
    public ProfileResponseDto getMyPage(User user) {
        return new ProfileResponseDto(user);
    }


    // 비밀번호 확인
    public boolean confirmPassword(UserDetailsImpl userDetails, String password) {
        if(passwordEncoder.matches(password, userDetails.getPassword())) {
            return true;
        } else {
            return false;
        }
    }


    // 비밀번호 변경
    @Transactional
    public ResponseEntity<String> updatePassword(User user, String newPassword) {
        String password = passwordEncoder.encode(newPassword);
        user.setPassword(password);
        return ResponseEntity.ok("Success");
    }


}
