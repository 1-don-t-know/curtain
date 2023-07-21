package com.sparta.curtain.controller;

import com.sparta.curtain.dto.ApiResponseDto;
import com.sparta.curtain.dto.PostListResponseDto;
import com.sparta.curtain.dto.UserListResponseDto;
import com.sparta.curtain.entity.UserRoleEnum;
import com.sparta.curtain.security.UserDetailsImpl;
import com.sparta.curtain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.method.P;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {


    private final UserService userService;

    // 전체 회원 목록 조회
    @Secured(UserRoleEnum.Authority.ADMIN)
    @GetMapping("/users")
    public ResponseEntity<UserListResponseDto> getUserList() {
        UserListResponseDto userList = userService.getUserList();
        return ResponseEntity.ok().body(userList);
    }

    // 이메일로 회원 검색
    @Secured(UserRoleEnum.Authority.ADMIN)
    @GetMapping("/users/{keyword}")
    public ResponseEntity<UserListResponseDto> searchUsers(@PathVariable String keyword) {
        UserListResponseDto userList = userService.searchUsers(keyword);
        return ResponseEntity.ok().body(userList);
    }

    // 회원 탈퇴
    @Secured(UserRoleEnum.Authority.ADMIN)
    @DeleteMapping("/users/delete")
    public ResponseEntity<ApiResponseDto> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body(new ApiResponseDto("회원 강제 탈퇴 성공", HttpStatus.OK.value()));

    }

}
