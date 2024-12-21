package org.example.newsfeed_project.Follow.repository;

import org.example.newsfeed_project.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
