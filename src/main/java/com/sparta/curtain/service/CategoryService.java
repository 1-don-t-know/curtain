package com.sparta.curtain.service;


import com.sparta.curtain.dto.ApiResponseDto;
import com.sparta.curtain.dto.CategoryResponseDto;
import com.sparta.curtain.dto.PostResponseDto;
import com.sparta.curtain.entity.Category;
import com.sparta.curtain.entity.Post;
import com.sparta.curtain.repository.CategoryRepository;
import com.sparta.curtain.repository.PostLikeRepository;
import com.sparta.curtain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public ResponseEntity<ApiResponseDto> getCategories() { //카테고리 전체 조회
        List<Category> categoryList = categoryRepository.findAllByOrderByIdAsc();

        List<CategoryResponseDto> newCategoryList = categoryList.stream().map(CategoryResponseDto::new).toList();

        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK.value(), "전체 카테고리 조회 성공", newCategoryList));
    }
    public ResponseEntity<ApiResponseDto> getCategoryPost(Long categoryId) { //카테고리 단 건 조회
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("맞는 카테고리가 없습니다") );


        List<Post> postList = postRepository.findAllByCategoryOrderByModifiedAtDesc(category);

        List<PostResponseDto> newPostList = postList.stream().map(PostResponseDto::new).toList();

        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK.value(),"카테고리 조회",newPostList));
    }

    public ResponseEntity<ApiResponseDto> getMainCategoryPost(Long categoryId) { //메인에 카테고리마다 좋아요 5개 상위 출력
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("맞는 카테고리가 없습니다") );

        List<Post> postList = postRepository.findAllByCategoryOrderByModifiedAtDesc(category);

        postList.sort(Comparator.comparingInt(Post::getLikeCount).reversed());

        int topCount = 5;
        List<Post> topPosts = postList.stream().limit(topCount).collect(Collectors.toList());

        List<PostResponseDto> mainPosts = topPosts.stream().map(PostResponseDto::new).toList();

        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK.value(),"좋아요 상위 5개 Post",mainPosts));
    }
}
