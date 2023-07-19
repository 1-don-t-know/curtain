package com.sparta.curtain.repository;

import com.sparta.curtain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCategory_IdOrderByModifiedAtDesc(Long category_Id);
}

