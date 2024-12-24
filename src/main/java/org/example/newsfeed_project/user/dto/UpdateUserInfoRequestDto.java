package org.example.newsfeed_project.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserInfoRequestDto {

	@Pattern(regexp = "^$|^(?!\\s*$).+", message = "공백은 입력할 수 없습니다.")
	private final String password;

	@Pattern(regexp = "^$|^(?!\\s*$).+", message = "공백은 입력할 수 없습니다.")
	private final String renterPassword;

	@Size(max = 10)
	private final String userName;

}
