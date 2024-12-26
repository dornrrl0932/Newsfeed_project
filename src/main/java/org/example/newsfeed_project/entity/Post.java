package org.example.newsfeed_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Table(name = "post")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @Column(length = 20)
    private String title;
    @Column(columnDefinition = "longtext")
    private String contents;
    @CreatedDate
    @Column(updatable = false) // 수정 불가
    private LocalDateTime createdAt;
    @CreatedDate
    @LastModifiedDate // 수정 날짜 반영
    private LocalDateTime updatedAt;
    @Column(name = "`like`")
    private Long like;
    @ManyToOne // post : user -> N:1
    @JoinColumn(name = "user_id") // user 테이블의 기본키 참조(user_id)
    private User user;


    public Post(String title, String contents, LocalDateTime createdDate, LocalDateTime updatedAt, Long like, User user){
        this.title = title;
        this.contents = contents;
        this.createdAt = createdDate;
        this.updatedAt = updatedAt;
        this.like = like;
        this.user = user;
    }
    public Post(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    public void updatedPost(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}