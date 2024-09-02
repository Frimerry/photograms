package com.cos.photogramstart.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;
	
	@Transactional
	public User toSignup(User user) {
		// 회원가입
		String orgPassword = user.getPassword();
		String encPassword = encoder.encode(orgPassword);
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");
		return userRepository.save(user);
	}

}
