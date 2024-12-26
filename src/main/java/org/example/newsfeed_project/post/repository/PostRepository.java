package org.example.newsfeed_project.post.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.example.newsfeed_project.common.exception.ResponseCode;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.web.server.ResponseStatusException;

public interface PostRepository extends JpaRepository<Post, Long> {
	/**
	 * 특정 유저의 게시물 10개 불러오기 (Post 타입으로 저장)
	 *     @Query (" SELECT p " +
	 *             "FROM Post p " +
	 *             "JOIN p.user u " +
	 *             "WHERE u = :user " +
	 *             "ORDER BY p.updatedDate DESC"
	 *     )
	 */
	Page<Post> findByUserOrderByUpdatedAtDesc(User user, Pageable pageable);

	Page<Post> findAll(Pageable pageable);

	Page<Post> findByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

	Optional<Post> findPostByPostId(Long postId);

	default Post findPostByPostIdOrElseThrow(Long postId) {
		return findPostByPostId(postId)
			.orElseThrow(() -> new ResponseStatusException(
				ResponseCode.POST_NOT_FOUND.getStatus(), ResponseCode.POST_NOT_FOUND.getMessage()));
	}

	@Query("SELECT p FROM Post p " +
			"JOIN Follow f ON p.user.userId = f.following.userId " +
			"WHERE f.follower.userId = :userId ")
	Page<Post> findPostsBySessionUser(@Param("userId") Long userId, Pageable pageable);


}
