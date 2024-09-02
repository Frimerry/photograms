package com.cos.photogramstart.web.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.constant.constant;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.service.LikesService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageApiController {
	
	private final ImageService imageService;
	private final LikesService likesService;
	
	/* 메인 페이지 목록 */
	@GetMapping("/api/image")
	public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PageableDefault(size=3) Pageable pageable){// 페이징 : 3건씩
		
		Page<Image> images = imageService.imageStory(principalDetails.getUser().getId(), pageable);
		return new ResponseEntity<>(new CMRespDto<>(constant.POSITIVE, "Success", images), HttpStatus.OK);
	}
	
	/* 이미지 게시물 상세보기 */
	@GetMapping("/api/image/{imageId}")
	public ResponseEntity<?> viewImage(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
		Image image = imageService.viewImage(imageId, principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(constant.POSITIVE, "Success", image), HttpStatus.OK);
	}
	
	/* 좋아요 */
	@PostMapping("/api/image/{imageId}/likes")
	public ResponseEntity<?> likes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
		
		likesService.likes(imageId, principalDetails.getUser().getId());
		
		return new ResponseEntity<>(new CMRespDto<>(constant.POSITIVE, "Like Success", null), HttpStatus.CREATED);
	}
	
	/* 좋아요 취소 */
	@DeleteMapping("/api/image/{imageId}/unlikes")
	public ResponseEntity<?> unlikes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
		
		likesService.unlikes(imageId, principalDetails.getUser().getId());
		
		return new ResponseEntity<>(new CMRespDto<>(constant.POSITIVE, "Unlike Success", null), HttpStatus.OK);
	}
	

}
