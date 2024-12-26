package org.example.newsfeed_project.user.repository;

import java.util.List;
import java.util.Optional;

import org.example.newsfeed_project.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserByEmail(String email);

	default User findUserByEmailOrElseThrow(String email) {
		return findUserByEmail(email)
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Dose not exist email: " + email));
	}

	Optional<User> findUserByUserId(Long id);

	default User findUserByUserIdOrElseThrow(Long id) {
		return findUserByUserId(id)
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Dose not exist userId: " + id));
	}
}
