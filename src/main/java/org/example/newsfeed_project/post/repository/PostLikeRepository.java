package org.example.newsfeed_project.post.repository;

import java.util.Optional;

import org.example.newsfeed_project.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

	@Query("select p from PostLike p where p.post.postId=:postId and p.user.userId=:userId")
	Optional<PostLike> findByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);

}
