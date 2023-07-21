package com.sparta.curtain.service;


import com.sparta.curtain.dto.*;
import com.sparta.curtain.entity.*;

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
    private final CategoryRepository categoryRepository;
    private final PostLikeRepository postLikeRepository;




    //게시글 생성 카테고리 id필요
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {

        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(
                () -> new IllegalArgumentException("ID값이 없습니다") );

        Post post = new Post(requestDto,category);
        post.setUser(user);

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

        if (user.getUsername().equals(post.getUser().getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            // requestDto로부터 받은 게시글의 제목과 내용으로 해당 post 내용 수정하기
            postRepository.delete(post);
        } else {
            // 해당 post의 작성자가 아니라면 null 반환하기
            throw new RejectedExecutionException();
        }
    }
    //게시글 수정(관리자)
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {
        Post post = findPost(id);
        //게시글 작성자와 요청자가 같은지 또는 관리자인지 체크 -> 아닐시 예외 발생
        // 해당 post의 작성자가 맞는지 확인
        if (user.getUsername().equals(post.getUser().getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            // requestDto로부터 받은 게시글의 제목과 내용으로 해당 post 내용 수정하기
            post.setTitle(requestDto.getTitle());
            post.setContent(requestDto.getContent());
            Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(() ->
                    new IllegalArgumentException("선택한 게시글이 존재하지 않습니다"));
            post.setCategory(category);


        } else {
            // 해당 post의 작성자가 아니라면 null 반환하기
            throw new RejectedExecutionException();
        }

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



    public PostListResponseDto searchPosts(String keyword) {
        List<PostResponseDto> postList = postRepository.findByTitleContaining(keyword).stream().map(PostResponseDto::new).collect(Collectors.toList());
        return new PostListResponseDto(postList);
    }

}
