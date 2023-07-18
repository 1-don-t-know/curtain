package com.spatra.curtain.dto;

import com.spatra.curtain.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileResponseDto {
    private String email;
    private String username;
    private List<PostResponseDto> posts;


    public ProfileResponseDto(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}
