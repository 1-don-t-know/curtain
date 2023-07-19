package com.sparta.curtain.dto;

import com.sparta.curtain.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class UserListResponseDto {
    private List<UserResponseDto> userList;

    public UserListResponseDto(List<UserResponseDto> userList) {
        this.userList = userList;
    }
}
