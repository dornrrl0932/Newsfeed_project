package org.example.newsfeed_project.comment.service;

import org.example.newsfeed_project.comment.dto.CommentDto;
import org.example.newsfeed_project.comment.dto.CommentRequestDto;
import org.example.newsfeed_project.comment.repository.CommentRepository;
import org.example.newsfeed_project.entity.Comment;
import org.example.newsfeed_project.entity.Post;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.post.repository.PostRepository;
import org.example.newsfeed_project.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    // 댓글 작성
    public CommentDto saveComment(Long postId, Long userId, CommentRequestDto requestDto) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다.");
        }
        Post findPost = optionalPost.get();
        User findUser = optionalUser.get();


        Comment comment = new Comment(findPost, findUser,requestDto.getComment(), 0L, LocalDateTime.now());
		comment =  commentRepository.save(comment);
		return new CommentDto(comment.getComments(), comment.getLike_count(), comment.getUser().getUserName(), comment.getUpdatedAt());
    }
    // 댓글 조회

    // 댓글 수정

    // 댓글 삭제
}
