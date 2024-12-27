package org.example.newsfeed_project.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Setter
	@Column
	private Long likeCount = 0L; //좋아요 개수

	@Setter
	@Column
	private Long commentCount = 0L; //댓글 개수

	@ManyToOne // post : user -> N:1
	@JoinColumn(name = "user_id") // user 테이블의 기본키 참조(user_id)
	private User user;

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
