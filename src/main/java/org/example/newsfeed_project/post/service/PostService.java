package org.example.newsfeed_project.post.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.post.dto.PostFindByPageRequestDto;
import org.example.newsfeed_project.post.dto.PostFindByPageResponseDto;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.post.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {
    public final PostRepository postRepository;

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
}
