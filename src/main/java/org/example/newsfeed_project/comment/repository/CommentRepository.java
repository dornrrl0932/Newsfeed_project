package org.example.newsfeed_project.comment.repository;

import java.util.Optional;

import org.example.newsfeed_project.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	// 설정한 페이징 조건으로 댓글 조회
	Page<Comment> findByPost_PostId(long postId, Pageable pageable);

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
	void updateComment(@Param("commentId") Long commentId, @Param("comments") String comments);

	default Comment findByCommentIdOrElseThrow(Long commentId) {
		return findById(commentId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));
	}
}