package com.cos.photogramstart.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor	// final필드 DI
@Controller
public class AuthController {
	
	// private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
	private final AuthService authService;
	
	//public AuthController(AuthService authService) {
	//	this.authService = authService;
	//}

	/* 로그인 페이지 */
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	/* 회원가입 페이지 */
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	/* 회원가입 */
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {
		
		User user = signupDto.toEntity();
		authService.toSignup(user);
		return "auth/signin";
	}

}
