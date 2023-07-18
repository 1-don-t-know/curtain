package com.spatra.curtain.controller;

import com.spatra.curtain.dto.ApiResponseDto;
import com.spatra.curtain.security.UserDetailsImpl;
import com.spatra.curtain.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentLikeController {
    private final CommentLikeService commentLikeService;
    @PostMapping("/post/{post_id}/comments/{comment_id}/likes")
    public ResponseEntity<ApiResponseDto> toggleLikeOnComment(@PathVariable Long post_id, @PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            boolean isLiked = commentLikeService.checkLikedComment(comment_id , userDetails.getUser());

            if(isLiked) {
                commentLikeService.removeLikeOnComment(post_id,comment_id,userDetails.getUser());
                return ResponseEntity.ok().body( new ApiResponseDto("좋아요 취소완료", HttpStatus.OK.value()));
            } else {
                commentLikeService.addLikeOnComment(post_id, comment_id,userDetails.getUser());
                return ResponseEntity.ok().body(new ApiResponseDto("", HttpStatus.CREATED.value()));
            }
        } catch (RejectedExecutionException e){
            return ResponseEntity.badRequest().body(new ApiResponseDto("유효하지 않는 토큰 입니다.", HttpStatus.UNAUTHORIZED.value()));
        }
    }
}
