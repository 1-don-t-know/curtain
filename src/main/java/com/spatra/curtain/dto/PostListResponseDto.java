package com.spatra.curtain.dto;

import java.util.List;

public class PostListResponseDto {
    private List<PostResponseDto> postList;

    public PostListResponseDto(List<PostResponseDto> postList) {
        this.postList = postList;
    }
}