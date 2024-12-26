package org.example.newsfeed_project.post.repository;

import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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


	Optional<Post> findPostByPostId (Long id);

	default Post findPostByPostIdOrElseThrow (Long id) {
		return findPostByPostId(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Dose not exist postId:" + id));
	}
}
