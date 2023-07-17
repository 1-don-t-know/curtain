package com.spatra.curtain.controller;

import com.spatra.curtain.dto.ApiResponseDto;
import com.spatra.curtain.dto.SignupRequestDto;
import com.spatra.curtain.jwt.JwtUtil;
import com.spatra.curtain.service.MailSenderService;
import com.spatra.curtain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final MailSenderService mailSenderService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        try {
            String authKey = mailSenderService.sendSimpleMessage(signupRequestDto.getEmail());
            signupRequestDto.setAuthKey(authKey);
            userService.signup(signupRequestDto);//회원 가입을 처리하기 위해 userService.signup(requestDto)를 호출
        } catch (IllegalArgumentException e) { // 중복된 유저가 있는 경우
            return ResponseEntity.badRequest().body(new ApiResponseDto("이미 존재하는 유저입니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(201).body(new ApiResponseDto("회원가입이 완료되었습니다.",HttpStatus.CREATED.value()));
    }
    // 로그인login
//    @PostMapping("/login")
    // 로그아웃
//    @PostMapping("/user/logout")
}
