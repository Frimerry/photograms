package com.cos.photogramstart.web;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserContorller {
	
	private final UserService userService;

	/* 프로필 페이지 */
	@GetMapping("/user/{pageUserId}")
	public String profile(@PathVariable int pageUserId, Model model,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		UserProfileDto dto = userService.profileView(pageUserId, principalDetails.getUser().getId());
		model.addAttribute("dto", dto);
		return "user/profile";
	}
	
	/* 회원정보 수정 페이지 */
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, 
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("세션정보:"+ principalDetails.getUser());
		
		return "user/update";
	}
}
