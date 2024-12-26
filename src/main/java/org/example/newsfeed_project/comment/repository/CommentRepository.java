package org.example.newsfeed_project.comment.repository;

import java.util.Optional;

import org.example.newsfeed_project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Query("SELECT c " +
		"FROM Comment c " +
		"WHERE c.commentId = :commentId " +
		"AND c.post.postId = :postId"
	)
	Optional<Comment> findByCommentIdAndPostId(@Param("commentId") Long commentId, @Param("postId") Long postId);

	@Modifying
	@Transactional(rollbackFor = Exception.class)
	@Query("UPDATE Comment c " +
		"SET c.comments = :comments " +
		"WHERE c.commentId = :commentId"
	)
	int updateComment(@Param("commentId") Long commentId, @Param("comments") String comments);
}