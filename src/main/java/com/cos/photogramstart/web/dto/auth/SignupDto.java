package com.cos.photogramstart.web.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data	// Getter, Setter
public class SignupDto {
	// RequestDto
	
	@Size(min=2, max=20, message = "사용자명은 2자 이상 20자 이하이어야 합니다.")
	private String username;
	@NotBlank(message = "암호는 필수입니다.")
	private String password;
	@NotBlank(message = "이메일은 필수입니다.")
	private String email;
	@NotBlank(message = "이름은 필수입니다.")
	private String name;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
	}
}
