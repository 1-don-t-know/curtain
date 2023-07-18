package com.spatra.curtain.repository;

import com.spatra.curtain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOOrderByCreatedAtDesc();
}