package com.sparta.curtain.service;


import com.sparta.curtain.dto.ApiResponseDto;
import com.sparta.curtain.dto.PostRequestDto;
import com.sparta.curtain.dto.PostResponseDto;
import com.sparta.curtain.entity.*;
import com.sparta.curtain.dto.PostListResponseDto;

import com.sparta.curtain.repository.CategoryRepository;
import com.sparta.curtain.repository.PostLikeRepository;

import com.sparta.curtain.repository.PostRepository;
import com.sun.jdi.request.DuplicateRequestException;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final CategoryRepository categoryRepository;



    //게시글 생성 카테고리 id필요
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto);
        post.setUser(user);
        Long category_id = requestDto.getCategory_id();
        Category category = categoryRepository.findById(category_id).orElseThrow(
                () -> new IllegalArgumentException("선택한 카테고리가 존재하지 않습니다")
        );
        post.setCategory(category);
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

    // 게시글 좋아요
    @Transactional
    public void likePost(Long id, User user) {
        Post post = findPost(id);
        if (postLikeRepository.findByUserAndPost(user, post).isPresent()) {
            throw new DuplicateRequestException("이미 좋아요를 한 게시글 입니다.");
        } else {
            post.setLikeCount(post.getLikeCount()+1);
            PostLike postLike = new PostLike(user, post);
            postLikeRepository.save(postLike);
        }
    }

    @Transactional
    public void cancelLikePost(Long id, User user) {
        Post post = findPost(id);
        Optional<PostLike> postLike = postLikeRepository.findByUserAndPost(user, post);
        if (postLike.isPresent()) {
            post.setLikeCount(post.getLikeCount() -1);
            postLikeRepository.delete(postLike.get());
        } else {
            throw new IllegalArgumentException("좋아요를 누른 적이 없는 게시글 입니다.");
        }
    }


    public Post findPost(long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글이 존재하지 않습니다")
        );
    }

}
