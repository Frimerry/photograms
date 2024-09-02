package com.cos.photogramstart.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {
	
	private final ImageService imageService;
	
	/* 메인 페이지 */
	@GetMapping({"/", "/image/story"})
	public String story() {
		return "image/story";
	}
	
	/* 인기 게시물 페이지 */
	@GetMapping("/image/popular")
	public String popular(Model model) {
		
		List<Image> images = imageService.popularList();
		
		model.addAttribute("images", images);
		return "image/popular";
	}
	
	/* 사진 업로드 페이지 */
	@GetMapping("/image/upload")
	public String upload() {
		return "image/upload";
	}
	
	/* 사진 업로드 실행 */
	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidationException("첨부된 이미지가 없습니다.", null);
		}
		else {
			imageService.uploadImage(imageUploadDto, principalDetails);
			return "redirect:/user/" + principalDetails.getUser().getId();
		}
	}

}
