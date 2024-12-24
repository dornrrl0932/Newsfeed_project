package org.example.newsfeed_project.comment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts/{post_id}/comments")
@RequiredArgsConstructor
public class CommentController {

}
