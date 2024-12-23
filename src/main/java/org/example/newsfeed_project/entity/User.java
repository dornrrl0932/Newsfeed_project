package org.example.newsfeed_project.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(length = 50, unique = true)
	private String email;

	private String password;

	@Column(length = 10)
	private String userName;

	@Column(length = 20)
	private String introduction; //회원 소개글

	private Boolean status; //회원 상태 (false: 탈퇴한 회원 의미)

	public User() {

	}

	public User(String email, String password, String userName) {
		this.email = email;
		this.password = password;
		this.userName = userName;
	}

}
