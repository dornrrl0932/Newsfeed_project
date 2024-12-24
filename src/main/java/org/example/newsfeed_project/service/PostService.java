package org.example.newsfeed_project.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.dto.NewFeedRequestDto;
import org.example.newsfeed_project.dto.NewFeedResponseDto;
import org.example.newsfeed_project.dto.PostCreateRequestDto;
import org.example.newsfeed_project.dto.PostCreateResponseDto;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.repository.PostRepository;
import org.example.newsfeed_project.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    public final PostRepository postRepository;
    public final UserRepository userRepository;

    public PostCreateResponseDto createPost(PostCreateRequestDto requestDto, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User findUser = optionalUser.get();
        Post post = new Post(requestDto.getTitle(), requestDto.getContents(), LocalDateTime.now(), LocalDateTime.now(), 0L, findUser);
        Post savePost = postRepository.save(post);
        return new PostCreateResponseDto(savePost.getTitle(), savePost.getContents());
    }

    public Map<Long, List<NewFeedResponseDto>> findPostAll(Long requestPage, Long pageSize, NewFeedRequestDto requestDto) {

        List<Post> findPostList = postRepository.findAll();
        for (Post post : findPostList) {
            System.out.println(post.getContents());
        }
        return switch (requestDto.getOrder()) {
            case "like" -> sortAndMapByLikedDesc(findPostList, pageSize, requestPage);
            case "update" -> sortAndMapByUpdatedDateDesc(findPostList, pageSize, requestPage);
            default -> throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        };
    }
    private Map<Long, List<NewFeedResponseDto>> sortAndMapByLikedDesc(List<Post> findPostList, Long pageSize, Long requestPage) {
        Map<Long, List<NewFeedResponseDto>> result = new HashMap<>();
        List<NewFeedResponseDto> newFeedResponseDtoArrayList = new ArrayList<>();
        Long pageCount = 0L;
        Long count = 0L;
        findPostList.sort(Comparator.comparing(Post::getLiked).reversed());

        for (Post post : findPostList) {
            if (count == pageSize) {
                newFeedResponseDtoArrayList.clear();
                count = 0L;
                pageCount++;
            }
            if(count == 0){
                result.put(pageCount, new ArrayList<>(newFeedResponseDtoArrayList));
            }
            if(pageCount > requestPage){
                break;
            }
            result.get(pageCount).add(new NewFeedResponseDto(post.getPostId(), post.getTitle(), post.getContents(), post.getUpdatedDate()));
            count++;
        }
        return result;
    }

    private Map<Long, List<NewFeedResponseDto>> sortAndMapByUpdatedDateDesc(List<Post> findPostList, Long pageSize, Long requestPage) {
        Map<Long, List<NewFeedResponseDto>> result = new HashMap<>();
        List<NewFeedResponseDto> newFeedResponseDtoArrayList = new ArrayList<>();
        Long pageCount = 0L;
        Long count = 0L;
        findPostList.sort(Comparator.comparing(Post::getUpdatedDate).reversed());

        for (Post post : findPostList) {
            if (count == pageSize) {
                newFeedResponseDtoArrayList.clear();
                count = 0L;
                pageCount++;
            }
            if(count == 0){
                result.put(pageCount, new ArrayList<>(newFeedResponseDtoArrayList));
            }
            if(pageCount > requestPage){
                break;
            }
            result.get(pageCount).add(new NewFeedResponseDto(post.getPostId(), post.getTitle(), post.getContents(), post.getUpdatedDate()));
            count++;
        }
        return result;
    }
}
