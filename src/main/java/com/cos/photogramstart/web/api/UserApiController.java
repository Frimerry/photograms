package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.constant.constant;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {
	
	private final UserService userService;
	private final SubscribeService subscribeService;
	
	@PutMapping("/api/user/{principalId}/profileImageUrl")
	public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId, 
			MultipartFile profileImageFile,
			@AuthenticationPrincipal PrincipalDetails principalDetails){	// form태그 name=profileImageFile와 동일해야함
		
		User userEntity = userService.profileImageUpdate(principalId, profileImageFile);
		principalDetails.setUser(userEntity);
		return new ResponseEntity<>(new CMRespDto<>(constant.POSITIVE, "Profile Image Changed Successfully", null), HttpStatus.OK);
	}
	
	/* 구독목록 */
	@GetMapping("/api/user/{pageUserId}/subscribe")
	public ResponseEntity<?> subscribeList(@PathVariable int pageUserId,
			@AuthenticationPrincipal PrincipalDetails principalDetails){
		
		List<SubscribeDto> subDto = subscribeService.subscriptionList(principalDetails.getUser().getId(), pageUserId);
		return new ResponseEntity<>(new CMRespDto<>(constant.POSITIVE, "Subscription List Loaded.", subDto), HttpStatus.OK);
	}
	
	/* 회원정보 수정 */
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(@PathVariable int id,
			@Valid UserUpdateDto userUpdateDto,
			BindingResult bindingResult, // @Valid 다음 파라미터 위치에서만 동작
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error:bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new CustomValidationApiException("Fail to update User Infomation!!!", errorMap);
		}
		else {
			User userEntity = userService.userUpdate(id, userUpdateDto.toEntity());
			principalDetails.setUser(userEntity);
			
			return new CMRespDto<>(constant.POSITIVE, "회원정보 수정완료", userEntity);
		}
	}

}
