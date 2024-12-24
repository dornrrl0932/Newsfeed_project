package org.example.newsfeed_project.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed_project.dto.CreatedPostRequestDto;
import org.example.newsfeed_project.dto.CreatedPostResponseDto;
import org.example.newsfeed_project.dto.UpdatedPostRequestDto;
import org.example.newsfeed_project.dto.UpdatedPostResponseDto;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    //created
    public CreatedPostResponseDto createdPost (CreatedPostRequestDto createdRequest) {
        Post post = new Post(createdRequest.getTitle(), createdRequest.getContents());
        Post savePost = postRepository.save(post);
        return new CreatedPostResponseDto(
                savePost.getTitle(),
                savePost.getContents());
    }
    //update
    @Transactional
    public UpdatedPostResponseDto updatePost(Long post_id, UpdatedPostRequestDto updatePostRequest) {
       Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
       post.updatedPost(updatePostRequest.getTitle(), updatePostRequest.getContents());
       return new UpdatedPostResponseDto(post.getTitle(), post.getContents());
    }
    @Transactional
    public void deletePost(Long post_id) {
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        postRepository.deleteById(post_id);
    }
}
