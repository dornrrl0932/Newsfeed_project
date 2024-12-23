package org.example.newsfeed_project.user.service;

import lombok.RequiredArgsConstructor;

import org.example.newsfeed_project.user.dto.LoginRequestDto;
import org.example.newsfeed_project.user.dto.SignUpRequestDto;
import org.example.newsfeed_project.user.encoder.PasswordEncoder;
import org.example.newsfeed_project.entity.User;
import org.example.newsfeed_project.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	//회원 가입
	public void signupUser(SignUpRequestDto signUpRequestDto) {

		//회원 생성
		User user = new User(signUpRequestDto.getEmail(), signUpRequestDto.getPassword(),
			signUpRequestDto.getUserName());

		//DB에 저장
		userRepository.save(user);

	}

	//로그인
	public User login(LoginRequestDto loginRequestDto) {

		User user = userRepository.findUserByEmailOrElseThrow(loginRequestDto.getEmail());

		//비밀번호 검증
		if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password.");
		}
		return user;
	}
}
