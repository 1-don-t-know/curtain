package com.sparta.curtain.controller;

import com.sparta.curtain.dto.PostListResponseDto;
import com.sparta.curtain.dto.UserListResponseDto;
import com.sparta.curtain.entity.UserRoleEnum;
import com.sparta.curtain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
