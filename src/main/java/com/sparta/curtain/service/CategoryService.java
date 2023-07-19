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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public ResponseEntity<ApiResponseDto> getCategories() {
        List<Category> categoryList = categoryRepository.findAllByOrderByIdAsc();

        List<CategoryResponseDto> newCategoryList = categoryList.stream().map(CategoryResponseDto::new).toList();

        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK.value(), "전체 카테고리 조회 성공", newCategoryList));
    }
    public ResponseEntity<ApiResponseDto> getCategoryPost(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("맞는 카테고리가 없습니다") );


        List<Post> postList = postRepository.findAllByCategoryOrderByModifiedAtDesc(category);

        List<PostResponseDto> newPostList = postList.stream().map(PostResponseDto::new).toList();

        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK.value(),"카테고리 조회",newPostList));
    }
}
