package com.sparta.curtain.repository;

import com.sparta.curtain.entity.CommentLike;
import com.sparta.curtain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {

    CommentLike findByCommentIdAndUser(Long commentId, User user);
    CommentLike findByPostIdAndUser(Long id, User user);
}
