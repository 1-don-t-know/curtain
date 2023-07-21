package com.sparta.curtain.service;

import com.sparta.curtain.dto.CommentResponseDto;
import com.sparta.curtain.entity.*;
import com.sparta.curtain.dto.CommentRequestDto;
import com.sparta.curtain.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {
        Post post = postService.findPost(requestDto.getPostId());
        Comment comment = new Comment(requestDto.getBody());
        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        if(user.getUsername().equals(comment.getUser().getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow();

        // 요청자가 운영자 이거나 댓글 작성자(post.user) 와 요청자(user) 가 같은지 체크
        if (user.getUsername().equals(comment.getUser().getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment.setBody(requestDto.getBody());
        } else {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        return new CommentResponseDto(comment);
    }
}
