package com.spatra.curtain.repository;

import com.spatra.curtain.entity.Post;
import com.spatra.curtain.entity.PostLike;
import com.spatra.curtain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
}