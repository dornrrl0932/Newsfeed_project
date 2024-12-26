package org.example.newsfeed_project.post.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.exception.ValidateException;
import org.example.newsfeed_project.post.dto.CreatedPostRequestDto;
import org.example.newsfeed_project.post.dto.CreatedPostResponseDto;
import org.example.newsfeed_project.post.dto.UpdatedPostRequestDto;
import org.example.newsfeed_project.post.dto.UpdatedPostResponseDto;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.post.repository.PostRepository;
import org.example.newsfeed_project.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //created
    public CreatedPostResponseDto createdPost (Long userId, CreatedPostRequestDto createdRequest) {
        User user = userRepository.findUserByUserIdOrElseThrow(userId);
        Post post = new Post(user, createdRequest.getTitle(), createdRequest.getContents());
        Post savePost = postRepository.save(post);
        return new CreatedPostResponseDto(
                user.getUserName(),
                savePost.getTitle(),
                savePost.getContents(),
                savePost.getUpdatedAt());
    }
    //update
    @Transactional
    public UpdatedPostResponseDto updatePost(Long userId, Long postId, UpdatedPostRequestDto updatePostRequest) {
       User user = userRepository.findUserByUserIdOrElseThrow(userId);
       Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
       if(userId != post.getUser().getUserId()) {
           throw new ValidateException("게시글 작성자가 아닙니다.", HttpStatus.UNAUTHORIZED);
       }
       post.updatedPost(updatePostRequest.getTitle(), updatePostRequest.getContents());
       return new UpdatedPostResponseDto(post.getTitle(), post.getContents(), post.getUpdatedAt());
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {
        User user = userRepository.findUserByUserIdOrElseThrow(userId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        if(userId != post.getUser().getUserId()) {
            throw new ValidateException("게시글 작성자가 아닙니다", HttpStatus.UNAUTHORIZED);
        }
        postRepository.deleteById(postId);
    }
}
