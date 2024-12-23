package org.example.newsfeed_project.user.repository;

import java.util.Optional;

import org.example.newsfeed_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserByEmail(String email);

	default User findUserByEmailOrElseThrow(String email) {
		return findUserByEmail(email)
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.UNAUTHORIZED, "Dose not exist email: " + email));
	}
}
