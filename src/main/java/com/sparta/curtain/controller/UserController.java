package com.sparta.curtain.controller;

import com.sparta.curtain.dto.ApiResponseDto;
import com.sparta.curtain.dto.AuthRequestDto;
import com.sparta.curtain.dto.SignupRequestDto;
import com.sparta.curtain.jwt.JwtUtil;
import com.sparta.curtain.service.MailSenderService;
import com.sparta.curtain.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final MailSenderService mailSenderService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        try {
           // String authKey = mailSenderService.sendSimpleMessage(signupRequestDto.getEmail());
            // signupRequestDto.setAuthKey(authKey);
            userService.signup(signupRequestDto);//회원 가입을 처리하기 위해 userService.signup(requestDto)를 호출
        } catch (IllegalArgumentException e) { // 중복된 유저가 있는 경우
            return ResponseEntity.badRequest().body(new ApiResponseDto("이미 존재하는 유저입니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(201).body(new ApiResponseDto("회원가입이 완료되었습니다.", HttpStatus.CREATED.value()));
    }

    // 로그인login
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody AuthRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        try {
            userService.login(loginRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getEmail(), loginRequestDto.getRole()));

        return ResponseEntity.ok().body(new ApiResponseDto("로그인 성공", HttpStatus.OK.value()));
    }

    // 메일확인
//    @GetMapping("auth/confirmSignup")
//    public ResponseEntity<ApiResponseDto> viewConfirmEmail(@RequestParam String email,@RequestParam String authKey) { //email 과 authKey 를 쿼리 파라미터(RequestParam)로 받아와서 처리
//        userService.confirmEmail(email,authKey); // 이메일 주소와 인증 키를 사용하여 사용자의 이메일 인증
//
//        //return "redirect:/login";
//        return ResponseEntity.ok().body(new ApiResponseDto("이메일 인증 성공", HttpStatus.OK.value()));
//    }

    // 로그아웃
    @PostMapping("/user/logout")
    // 헤더에서 Authorization 받아오기
    public ResponseEntity<ApiResponseDto> logout(@RequestHeader("Authorization") String authorizationHeader) {
        String token = extractTokenFromHeader(authorizationHeader);
        //로그아웃 처리
        userService.logout(token);
        return ResponseEntity.ok().body(new ApiResponseDto("로그아웃 성공", HttpStatus.OK.value()));
    }

    // 헤더 값에서 토큰 반환
    private String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }


}

