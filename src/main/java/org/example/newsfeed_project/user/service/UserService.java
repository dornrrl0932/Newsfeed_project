package org.example.newsfeed_project.user.service;

import java.util.Optional;

import org.example.newsfeed_project.common.exception.EmailAlreadyExistsException;
import org.example.newsfeed_project.common.exception.PasswordAuthenticationException;
import org.example.newsfeed_project.common.exception.ResponseCode;
import org.example.newsfeed_project.common.exception.UserDeletedException;
import org.example.newsfeed_project.common.exception.ValidateException;

import org.example.newsfeed_project.entity.User;

import org.example.newsfeed_project.follow.repository.FollowRepository;
import org.example.newsfeed_project.post.repository.PostRepository;
import org.example.newsfeed_project.user.repository.UserRepository;

import org.example.newsfeed_project.user.dto.*;
import org.example.newsfeed_project.user.encoder.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final PostRepository postRepository;
	private final FollowRepository followRepository;

	//회원 가입
	public void signupUser(SignUpRequestDto signUpRequestDto) {

		//비밀번호 확인 : 입력한 비밀번호와 확인 비밀번호가 일치하는지 확인
		if (!signUpRequestDto.getPassword().equals(signUpRequestDto.getRenterPassword())) {
			throw new PasswordAuthenticationException(ResponseCode.PASSWORD_MISMATCH);
		}

		//이메일 중복 확인
		if (userRepository.findUserByEmail(signUpRequestDto.getEmail()).isPresent()) {
			throw new EmailAlreadyExistsException(ResponseCode.EMAIL_ALREADY_EXISTS);
		}

		//비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

		//회원 생성
		User user = new User(signUpRequestDto.getEmail(), encodedPassword, signUpRequestDto.getUserName());

		//DB에 저장
		userRepository.save(user);
	}

	//회원 탈퇴
	@Transactional(rollbackFor = Exception.class)
	public void CancelUser(Long userId, CancelRequestDto cancelRequestDto) {

		// User 조회
		User user = userRepository.findUserByUserIdOrElseThrow(userId);

		// 이미 탈퇴한 회원인지 확인
		if (!user.getStatus()) {
			throw new UserDeletedException(ResponseCode.USER_ALREADY_DELETE);
		}

		// 기존 비밀번호와 입력한 비밀번호가 일치하는지 확인, 입력한 비밀번호와 확인 비밀번호가 일치하는지 확인
		if (!passwordEncoder.matches(cancelRequestDto.getRenterPassword(), user.getPassword())
			&& !cancelRequestDto.getPassword().equals(cancelRequestDto.getRenterPassword())) {
			throw new PasswordAuthenticationException(ResponseCode.PASSWORD_MISMATCH);
		}

		//비밀번호 확인 : 입력한 비밀번호와 확인 비밀번호가 일치하는지 확인
		if (!cancelRequestDto.getPassword().equals(cancelRequestDto.getRenterPassword())) {
			throw new PasswordAuthenticationException(ResponseCode.PASSWORD_MISMATCH);
		}

		// 탈퇴 유저의 게시물 삭제
		postRepository.deleteByUserId(userId);
		// 탈퇴 유저의 팔로우 관계 삭제
		followRepository.deleteByFollowerOrFollowing(userId);

		//탈퇴처리 -> 회원 상태 false로 변경
		user.setStatus(false);

		//deleteById는 하드 삭제를 수행 -> save로 현재 상태를 업데이트
		userRepository.save(user);
	}

	//로그인
	public User login(LoginRequestDto loginRequestDto) {

		User user = userRepository.findUserByEmailOrElseThrow(loginRequestDto.getEmail());

		//비밀번호 검증
		if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
			throw new ResponseStatusException(ResponseCode.PASSWORD_MISMATCH.getStatus(),
				ResponseCode.PASSWORD_MISMATCH.getMessage());
		}
		return user;
	}

	//회원 정보 수정
	public void updateUserInfo(Long id, UpdateUserInfoRequestDto dto) {

		User findUser = userRepository.findUserByUserIdOrElseThrow(id);

		//비밀번호 null 값 확인 및 유효성 검증 후 수정
		Optional.ofNullable(dto.getPassword()).ifPresent(password -> {
			if (isValidPassword(dto, password, findUser)) {
				findUser.setPassword(passwordEncoder.encode(password));
			} else {
				throw new ValidateException(ResponseCode.PASSWORD_SAME_AS_BEFORE);
			}
		});

		//회원 이름 null 값 확인 후 수정
		Optional.ofNullable(dto.getUserName()).ifPresent(findUser::setUserName);

		userRepository.save(findUser);
	}

	//수정하려는 비빌번호의 유효성을 검증하는 메서드
	private boolean isValidPassword(UpdateUserInfoRequestDto dto, String password, User findUser) {

		boolean isPasswordSame = passwordEncoder.matches(password, findUser.getPassword());//암호화된 기존 비밀번호와 비교
		boolean isPasswordsMatch = password.equals(dto.getRenterPassword()); //입력한 비밀번호와 재입력 비밀번호가 같은지 비교

		return !isPasswordSame && isPasswordsMatch;
	}
}
