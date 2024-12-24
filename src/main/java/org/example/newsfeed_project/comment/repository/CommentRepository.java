package org.example.newsfeed_project.comment.repository;

import org.example.newsfeed_project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
