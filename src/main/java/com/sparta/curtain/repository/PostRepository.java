package com.sparta.curtain.repository;

import com.sparta.curtain.entity.Category;
import com.sparta.curtain.entity.Post;
import com.sparta.curtain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCategoryOrderByModifiedAtDesc(Category category);

    List<Post> findAllByUser(User user);

    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findByTitleContaining(String keyword);
}

