package org.example.newsfeed_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
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
    private LocalDateTime createdDate;
    @CreatedDate
    @LastModifiedDate // 수정 날짜 반영
    private LocalDateTime updatedDate;

    @ManyToOne // post : user -> N:1
    @JoinColumn(name = "user_id") // user 테이블의 기본키 참조(user_id)
    private User user;

}
