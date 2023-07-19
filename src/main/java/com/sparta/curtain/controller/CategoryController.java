package com.sparta.curtain.controller;


import com.sparta.curtain.dto.ApiResponseDto;
import com.sparta.curtain.service.CategoryService;
import com.sparta.curtain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;
    private final PostService postService;


    @GetMapping("/categories")
    public ResponseEntity<ApiResponseDto> getCategoryPost() {

        return categoryService.getCategories();
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<ApiResponseDto> getCategoryPost(@PathVariable Long id) {
        return categoryService.getCategoryPost(id);
    }

//    @PostMapping("/categories") // 카테고리 제작 -> 관리자만
//    public ResponseEntity<CategoryResponseDto> createCategory(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CategoryRequestDto requestDto) {
//        CategoryResponseDto result = categoryService.createCategory(requestDto, userDetails.getUser());
//        return ResponseEntity.status(201).body(result);
//    }
}
