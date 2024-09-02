package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	@Transactional(readOnly=true)
	public List<Image> popularList(){
		return imageRepository.mPopular();
	}
	
	/* 메인 스토리 목록 */
	@Transactional(readOnly=true)
	public Page<Image> imageStory(int principalId, Pageable pageable){
		
		Page<Image> images= imageRepository.mStory(principalId, pageable);
		
		// 이미지 좋아요 정보
		images.forEach((image)->{
			// 좋아요 갯수
			image.setLikeCount(image.getLikes().size());
			
			// 좋아요 상태
			image.getLikes().forEach((like)->{
				if(like.getUser().getId() == principalId) {
					image.setLikeState(true);
				}
			});
		});
		return images;
	}
	
	/* 이미지 게시글 상세보기 */
	@Transactional(readOnly=true)
	public Image viewImage(int id, int principalId) {
		Image image = imageRepository.findById(id);
		
		// 좋아요 갯수
		image.setLikeCount(image.getLikes().size());
		
		// 좋아요 상태
		image.getLikes().forEach((like)->{
			if(like.getUser().getId() == principalId) {
				image.setLikeState(true);
			}
		});
		return image;
	}
	
	/* 사진 업로드 경로 */
	@Value("${file.path}")	// application.yml
	private String uploadFolder;
	
	/* 사진 업로드 */
	@Transactional
	public void uploadImage(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		// 범용고유식별자
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid+ "_"+ imageUploadDto.getFile().getOriginalFilename();
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		imageRepository.save(image);
	}

}
