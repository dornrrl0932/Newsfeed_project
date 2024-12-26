package org.example.newsfeed_project.post.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.post.dto.PostFindByPageRequestDto;
import org.example.newsfeed_project.post.dto.PostFindByPageResponseDto;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    public final PostRepository postRepository;
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

    public Map<Long, List<PostFindByPageResponseDto>> findPostByPage(Long requestPage, Long pageSize, PostFindByPageRequestDto requestDto) {
        List<Post> findPostList = postRepository.findAll();
       return findPostByUpdateOrLikeDesc(findPostList,pageSize,requestPage,requestDto);
    }

    public PostFindByPageResponseDto findPostById(Long id){
       Optional<Post> optionalPost = postRepository.findById(id);
       if(optionalPost.isEmpty()){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }
       Post post = optionalPost.get();
       return new PostFindByPageResponseDto(post.getTitle(),post.getContents(),post.getUser().getUserName(), post.getUpdatedAt());
    }

    private Map<Long, List<PostFindByPageResponseDto>> findPostByUpdateOrLikeDesc(List<Post> findPostList, Long pageSize, Long requestPage, PostFindByPageRequestDto requestDto) {
        Map<Long, List<PostFindByPageResponseDto>> result = new HashMap<>();
        List<PostFindByPageResponseDto> postFindByPageResponseDtoArrayList = new ArrayList<>();
        Long pageCount = 0L;
        Long count = 0L;

        switch (requestDto.getOrder()) {
            case "like":
                findPostList.sort(Comparator.comparing(Post::getLike).reversed());
                break;
            case "update":
                findPostList.sort(Comparator.comparing(Post::getUpdatedAt).reversed());
                break;
            default:
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        for(Post post : findPostList) {
            if (count == pageSize) {
                postFindByPageResponseDtoArrayList.clear();
                count = 0L;
                pageCount++;
            }
            if (count == 0) {
                result.put(pageCount, new ArrayList<>(postFindByPageResponseDtoArrayList));
            }
            if (pageCount > requestPage) {
                break;
            }
            result.get(pageCount).add(new PostFindByPageResponseDto(post.getTitle(), post.getContents(),post.getUser().getUserName(), post.getUpdatedAt()));
            count++;
        }
        return result;
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
