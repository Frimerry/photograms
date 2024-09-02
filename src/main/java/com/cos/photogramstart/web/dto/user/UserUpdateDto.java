package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	
	/** 이름 */
	@NotBlank(message = "이름은 필수입니다.")
	private String name;
	/** 암호 */
	@NotBlank(message = "암호는 필수입니다.")
	private String password;
	/** 웹사이트 */
	private String website;
	/** 자기소개(바이오) */
	private String bio;
	/** 전화번호 */
	private String phone;
	/** 성별 */
	private String gender;
	
	public User toEntity() {
		return User.builder()
				.name(name)
				.password(password)
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
