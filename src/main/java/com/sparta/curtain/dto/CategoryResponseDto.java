package com.sparta.curtain.dto;


import com.sparta.curtain.entity.Category;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;

@Getter
public class CategoryResponseDto {
    private Long id;
    private String category_name;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.category_name = category.getCategory_name();

    }
}
