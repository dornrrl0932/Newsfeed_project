package org.example.newsfeed_project.user.repository;

import java.util.Optional;

import org.example.newsfeed_project.common.exception.ResponseCode;
import org.example.newsfeed_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.server.ResponseStatusException;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserByEmail(String email);

	default User findUserByEmailOrElseThrow(String email) {
		return findUserByEmail(email)
			.orElseThrow(() -> new ResponseStatusException(
				ResponseCode.EMAIL_NOT_FOUND.getStatus(), ResponseCode.EMAIL_NOT_FOUND.getMessage()));
	}

	Optional<User> findUserByUserId(Long id);

	default User findUserByUserIdOrElseThrow(Long id) {
		return findUserByUserId(id)
			.orElseThrow(() -> new ResponseStatusException(
				ResponseCode.USER_NOT_FOUND.getStatus(), ResponseCode.USER_NOT_FOUND.getMessage()));
	}
}
