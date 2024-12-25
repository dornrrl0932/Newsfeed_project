package org.example.newsfeed_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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

	//회원 상태 (false: 탈퇴한 회원 의미)
	// 소프트 삭제(기본값 ture)
	@Setter
	private Boolean status = true;

	public User() {

	}

	public User(String email, String password, String userName, String introduction) {
		this.email = email;
		this.password = password;
		this.userName = userName;
		this.introduction = introduction;
	}

	public User(String email, String password, String userName) {
		this(email, password, userName, null);
	}

	public void updateIntroduction(String introduction) {
		this.introduction = introduction;
	}
}
