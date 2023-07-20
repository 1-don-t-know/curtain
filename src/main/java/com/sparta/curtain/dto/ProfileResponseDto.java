package com.sparta.curtain.dto;

import com.sparta.curtain.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileResponseDto {
    private String email;
    private String username;
    private List<ProfilePostListResponseDto> posts;


    public ProfileResponseDto(User user, List<ProfilePostListResponseDto> postList) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.posts = postList;
    }

}
