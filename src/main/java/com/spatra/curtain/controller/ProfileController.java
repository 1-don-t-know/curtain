package com.spatra.curtain.controller;

import com.spatra.curtain.dto.PasswordRequestDto;
import com.spatra.curtain.dto.ProfileResponseDto;
import com.spatra.curtain.security.UserDetailsImpl;
import com.spatra.curtain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {
    private final UserService userService;


    @GetMapping("/my-page")
    ProfileResponseDto getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getMyPage(userDetails.getUser());
    }


    // 비밀번호 변경
    @PutMapping("/profile/password")
    ResponseEntity<String> updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordRequestDto requestDto) {
        boolean valid = userService.confirmPassword(userDetails, requestDto.getPassword());
        if (valid) {
            return userService.updatePassword(userDetails.getUser(), requestDto.getNewPassword());
        } else {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }

    }

}
