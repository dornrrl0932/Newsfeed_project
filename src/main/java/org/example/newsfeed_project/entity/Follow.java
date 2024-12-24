package org.example.newsfeed_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Builder
@NoArgsConstructor // 기본 생성자 Follow()
@AllArgsConstructor
@Table(name = "follow")
public class Follow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long followId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "following_id")
	private User following;  // 팔로잉 된 유저

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "follower_id")
	private User follower; // 팔로우 한 유저 (본인)

	@Setter
	private Boolean isApprove; // 승인: true, 미승인 false
}
