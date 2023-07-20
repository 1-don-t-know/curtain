package com.sparta.curtain.controller;

import com.sparta.curtain.dto.PasswordRequestDto;
import com.sparta.curtain.dto.ProfileResponseDto;
import com.sparta.curtain.security.UserDetailsImpl;
import com.sparta.curtain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {
    private final UserService userService;


    @GetMapping("/my-page")
    String getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        ProfileResponseDto profileResponseDto = userService.getMyPage(userDetails.getUser());

        model.addAttribute("user", profileResponseDto);
        model.addAttribute("posts", profileResponseDto.getPosts());
        return "mypage";
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
