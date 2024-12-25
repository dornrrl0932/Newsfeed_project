package org.example.newsfeed_project.comment.repository;

import java.util.Optional;

import org.example.newsfeed_project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Query("SELECT c " +
		"FROM Comment c " +
		"WHERE c.commentId = :commentId " +
		"AND c.post.postId = :postId"
	)
	Optional<Comment> findByCommentIdAndPostId(@Param("commentId") Long commentId, @Param("postId") Long postId);
}