package org.example.newsfeed_project.post.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed_project.post.dto.PostFindByDateRangeRequestDto;
import org.example.newsfeed_project.post.dto.PostFindByPageRequestDto;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.post.dto.PostPageDto;
import org.example.newsfeed_project.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {
    public final PostRepository postRepository;

    public List<PostPageDto> findPostByDateRange(int requestPage, int pageSize, PostFindByDateRangeRequestDto requestDto) {

        Pageable pageable = PageRequest.of(requestPage, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"));

        Page<Post> postPage = postRepository.findByUpdatedAtBetween(requestDto.startDate, requestDto.endDate, pageable);

        return PostPageDto.convertFrom(postPage);
    }

    public List<PostPageDto> findPostByPage(int requestPage, int pageSize, PostFindByPageRequestDto requestDto){
        Pageable pageable;
        switch (requestDto.getOrder()) {
            case "like":
                 pageable = PageRequest.of(requestPage, pageSize, Sort.by(Sort.Direction.DESC, "like_count"));
                break;
            case "update":
                 pageable = PageRequest.of(requestPage, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"));
                break;
            default:
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        List<PostPageDto> posts = PostPageDto.convertFrom(
                postRepository.findAll(pageable));
        return posts;
    }


}
