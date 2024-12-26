package org.example.newsfeed_project.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "comment")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Setter
	@Column(length = 250)
	private String comments;

	// 기본값 0
	@Setter
	private Long likeCount = 0L;

	@CreatedDate
	@LastModifiedDate
	private LocalDateTime updatedAt;

	public Comment(Post post, User user, String comments, Long likeCount, LocalDateTime updatedAt){
		this.post = post;
		this.user = user;
		this.comments = comments;
		this.likeCount = likeCount;
		this.updatedAt = updatedAt;
	}

}
