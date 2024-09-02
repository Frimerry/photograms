package com.cos.photogramstart.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.constant.constant;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {
	
	private final SubscribeService subscribeService;
	
	/* 구독하기 */
	@PostMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PathVariable int toUserId){
		subscribeService.subscribe(principalDetails.getUser().getId(), toUserId);
		return new ResponseEntity<>(new CMRespDto<>(constant.POSITIVE, "구독하였습니다.", null), HttpStatus.OK);
	}
	
	/* 구독취소하기 */
	@DeleteMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> unSubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PathVariable int toUserId){
		subscribeService.unSubscribe(principalDetails.getUser().getId(), toUserId);
		return new ResponseEntity<>(new CMRespDto<>(constant.POSITIVE, "구독취소하였습니다.", null), HttpStatus.OK);
	}

}
