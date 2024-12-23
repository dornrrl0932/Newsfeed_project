package org.example.newsfeed_project.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {


    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "올바른 이메일 형식을 작성해주세요")
    private final String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 8자 이상, 영문, 숫자, 특수문자를 최소 1개씩 포함하여 생성해주세요.")
    private final String password;

    @NotBlank(message = "비밀번호를 다시 입력해주세요.")
    private final String renterPassword;

    @NotBlank(message = "유저 이름은 필수 입력값입니다.")
    @Size(max = 10, message ="유저 이름은 최대 5자로 설정 가능합니다.")
    private final String userName;

}
