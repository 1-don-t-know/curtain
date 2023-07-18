package com.spatra.curtain.dto;

import com.spatra.curtain.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String email;
    private String username;


    public ProfileResponseDto(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}
