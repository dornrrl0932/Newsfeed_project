package org.example.newsfeed_project.post.service;

import org.example.newsfeed_project.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

	private final PostRepository postRepository;
}
