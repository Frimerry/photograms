package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class ImageUploadDto {
	
	/* 이미지 파일 */
	private MultipartFile file;	//@NotBlank 지원안되는 타입
	
	/* 이미지 설명 */
	private String caption;
	
	public Image toEntity(String postImageUrl, User user) {
		return Image.builder()
				.caption(caption)
				.user(user)
				.postImageUrl(postImageUrl)
				.build();
	}
}
