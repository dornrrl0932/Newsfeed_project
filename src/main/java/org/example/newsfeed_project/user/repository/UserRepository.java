package org.example.newsfeed_project.user.repository;

import java.util.Optional;

import org.example.newsfeed_project.common.exception.ResponseCode;
import org.example.newsfeed_project.common.exception.ValidateException;
import org.example.newsfeed_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserByEmail(String email);

	default User findUserByEmailOrElseThrow(String email) {
		return findUserByEmail(email)
			.orElseThrow(() -> new ValidateException(ResponseCode.EMAIL_NOT_FOUND));
	}

	Optional<User> findUserByUserId(Long id);

	default User findUserByUserIdOrElseThrow(Long id) {
		return findUserByUserId(id)
			.orElseThrow(() -> new ValidateException(ResponseCode.USER_NOT_FOUND));
	}

	List<User> userId(Long userId);
}
