package org.example.newsfeed_project.comment.repository;

import org.example.newsfeed_project.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	// 설정한 페이징 조건으로 댓글 조회
	Page<Comment> findByPost_PostId(long postId, Pageable pageable);
}
