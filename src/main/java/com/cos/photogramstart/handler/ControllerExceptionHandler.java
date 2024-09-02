package com.cos.photogramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.constant.constant;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
	
	/* 회원가입 오류, 사진첨부 오류 */
	@ExceptionHandler(CustomValidationException.class)
	public String validException(CustomValidationException e) {
		
		if(e.getErrorMap() == null) {
			return Script.back(e.getMessage());
		}
		else {
			return Script.back(e.getErrorMap().toString());
		}
	}
	
	/* 프로필페이지 사용자 정보없음 */
	@ExceptionHandler(CustomException.class)
	public String exception(CustomException e) {
		return Script.back(e.getMessage());
	}
	
	/* 회원정보 수정 */
	@ExceptionHandler(CustomValidationApiException.class)
	public ResponseEntity<?> validException(CustomValidationApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(constant.NEGATIVE, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
	}
	
	/* 구독 오류 */
	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<?> apiException(CustomApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(constant.NEGATIVE, e.getMessage(), null), HttpStatus.BAD_REQUEST);
	}

}
