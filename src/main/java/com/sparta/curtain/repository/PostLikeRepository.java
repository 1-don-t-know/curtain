package com.sparta.curtain.repository;

import com.sparta.curtain.entity.Post;
import com.sparta.curtain.entity.PostLike;
import com.sparta.curtain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
}
