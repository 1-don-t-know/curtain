package com.spatra.curtain.repository;

import com.spatra.curtain.entity.Comment;
import com.spatra.curtain.entity.CommentLike;
import com.spatra.curtain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {

    CommentLike findByCommentIdAndUser(Long commentId, User user);
    CommentLike findByPostIdAndUser(Long id, User user);
}
