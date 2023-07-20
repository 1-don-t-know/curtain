package com.sparta.curtain.dto;

import com.sparta.curtain.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfilePostListResponseDto {
    private String email;
    private String title;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public ProfilePostListResponseDto(Post post) {
        this.email = post.getUser().getEmail();
        this.title = post.getTitle();
        this.likeCount = post.getLikeCount();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
