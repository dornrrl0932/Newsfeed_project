package org.example.newsfeed_project.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(length = 50, unique = true)
	private String email;

	@Setter
	private String password;

	@Setter
	@Column(length = 10)
	private String userName;

	@Column(length = 20)
	private String introduction; //회원 소개글

	private Boolean status; //회원 상태 (false: 탈퇴한 회원 의미)

	public User() {

	}

	public User(String email, String password, String userName, String introduction) {
		this.email = email;
		this.password = password;
		this.userName = userName;
		this.introduction = introduction;
	}

	public void updateIntroduction(String introduction) {
		this.introduction = introduction;
	}
}
