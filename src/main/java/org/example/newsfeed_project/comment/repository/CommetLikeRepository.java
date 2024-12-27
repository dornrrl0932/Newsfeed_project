package org.example.newsfeed_project.comment.repository;

import java.util.Optional;

import org.example.newsfeed_project.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommetLikeRepository extends JpaRepository<CommentLike, Long> {

	@Query("select c from CommentLike c where c.comment.commentId=:commentId and c.user.userId=:userId")
	Optional<CommentLike> findByCommentIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);
}
