package org.example.newsfeed_project.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed_project.dto.PostRequestDto;
import org.example.newsfeed_project.dto.PostResponseDto;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.repository.PostRepository;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    //created
    public PostResponseDto createdPost (PostRequestDto createdRequest) {
        Post post = new Post(createdRequest.getTitle(), createdRequest.getContents());
        Post savePost = postRepository.save(post);
        return new PostResponseDto(
                savePost.getTitle(),
                savePost.getContents());
    }
}
