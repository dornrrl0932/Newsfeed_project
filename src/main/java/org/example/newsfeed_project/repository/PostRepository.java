package org.example.newsfeed_project.repository;

import org.example.newsfeed_project.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PostRepository extends JpaRepository<Post, Long> {
}
