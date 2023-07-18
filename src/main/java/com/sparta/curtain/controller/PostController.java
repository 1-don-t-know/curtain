package com.sparta.curtain.controller;


import com.sparta.curtain.dto.ApiResponseDto;
import com.sparta.curtain.dto.PostRequestDto;
import com.sparta.curtain.dto.PostResponseDto;
import com.sparta.curtain.service.PostService;
import com.sparta.curtain.dto.PostListResponseDto;
import com.sparta.curtain.security.UserDetailsImpl;

import com.sun.jdi.request.DuplicateRequestException;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;



    @PostMapping("/posts") //게시물 생성
    public ResponseEntity<PostResponseDto> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto requestDto) {
        PostResponseDto result = postService.createPost(requestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(result);
    }

    @GetMapping("/posts") //게시물 전체조회
    public ResponseEntity<PostListResponseDto> getPosts() {
        PostListResponseDto result = postService.getPosts();

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/posts/{id}") //게시물 단 건 조회
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        PostResponseDto result = postService.getPostById(id);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<ApiResponseDto> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        try {
            PostResponseDto result = postService.updatePost(id, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(result);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 수정 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/posts/{id}") //게시물 삭
    public ResponseEntity<ApiResponseDto> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            postService.deletePost(id, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("게시글 삭제 성공", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }



    // 게시글 좋아요
    @PostMapping("/posts/{id}/like")
    public ResponseEntity<ApiResponseDto> likePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            postService.likePost(id, userDetails.getUser());
        } catch (DuplicateRequestException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.ok().body(new ApiResponseDto("게시글 좋아요 성공", HttpStatus.OK.value()));
    }

    // 게시글 좋아요 취소
    @DeleteMapping("posts/{id}/like")
    public ResponseEntity<ApiResponseDto> cancelLikePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            postService.cancelLikePost(id, userDetails.getUser());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.ok().body(new ApiResponseDto("게시글 좋아요 취소 성공", HttpStatus.OK.value()));
    }
}
