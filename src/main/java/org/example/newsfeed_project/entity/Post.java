package org.example.newsfeed_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @ManyToOne // post : user -> N:1
    @JoinColumn(name = "user_id") // user 테이블의 기본키 참조(user_id)
    private User user;

    public Post() {}


    public Post(String title, String contents) {

        this.title = title;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updatedPost(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.updatedAt = LocalDateTime.now();
    }
}
