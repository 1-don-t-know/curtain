package com.spatra.curtain.service;


import com.spatra.curtain.dto.PostListResponseDto;
import com.spatra.curtain.dto.PostRequestDto;
import com.spatra.curtain.dto.PostResponseDto;
import com.spatra.curtain.entity.Post;

import com.spatra.curtain.entity.PostLike;
import com.spatra.curtain.entity.User;
import com.spatra.curtain.entity.UserRoleEnum;
import com.spatra.curtain.repository.PostLikeRepository;
import com.spatra.curtain.repository.PostRepository;
import com.sun.jdi.request.DuplicateRequestException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Optional;

import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final PostLikeRepository postLikeRepository;

    //게시글 생성 카테고리 id필요
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto);
        post.setUser(user);

        postRepository.save(post);

        return new PostResponseDto(post);
    }
    //게시글 전체 조회(카테고리별 x)
    public PostListResponseDto getPosts() {
        List<PostResponseDto> postList = postRepository.findAll().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        return new PostListResponseDto(postList);
    }
    //게시글 단건 조회(카테고리별 x)
    public PostResponseDto getPostById(Long id) {
        Post post = findPost(id);

        return new PostResponseDto(post);
    }

    //게시글 삭제(관리자)
    public void deletePost(Long id, User user) {
        Post post = findPost(id);
        //게시글 작성자와 요청자가 같은지 또는 관리자인지 체크 -> 아닐시 예외 발생
        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !post.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }

        postRepository.delete(post);
    }
    //게시글 수정(관리자)
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {
        Post post = findPost(id);
        //게시글 작성자와 요청자가 같은지 또는 관리자인지 체크 -> 아닐시 예외 발생
        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !post.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }

        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        return new PostResponseDto(post);
    }

    public Post findPost(long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글이 존재하지 않습니다")
        );
    }



    // 게시글 좋아좋요
    @Transactional
    public void likePost(Long id, User user) {
        Post post = findPost(id);
        if (postLikeRepository.findByUserAndPost(user, post).isPresent()) {
            throw new DuplicateRequestException("이미 좋아요를 한 게시글 입니다.");
        } else {
            PostLike postLike = new PostLike(user, post);
            postLikeRepository.save(postLike);
        }
    }

    @Transactional
    public void cancelLikePost(Long id, User user) {
        Post post = findPost(id);
        Optional<PostLike> postLike = postLikeRepository.findByUserAndPost(user, post);
        if (postLike.isPresent()) {
            postLikeRepository.delete(postLike.get());
        } else {
            throw new IllegalArgumentException("좋아요를 누른 적이 없는 게시글 입니다.");
        }
    }
}