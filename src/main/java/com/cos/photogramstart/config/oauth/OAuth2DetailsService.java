package com.cos.photogramstart.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService{
	
	private final UserRepository userRepository;
	// private final BCryptPasswordEncoder cycle Error : SecurityConfig보다 OAuth2DetailsService가 먼저등록됨
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User oauth2User = super.loadUser(userRequest);
		
		Map<String, Object> userInfo = oauth2User.getAttributes();
		
		String username = "google_" + (String)userInfo.get("sub");
		String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
		String email = (String)userInfo.get("email");
		String name = (String)userInfo.get("name");
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity==null){	// 구글 최초로그인 -> 회원가입
			
			User user = User.builder()
					.username(username)
					.password(password)	// 로그인에 password 사용안함
					.email(email)
					.name(name)
					.role("ROLE_USER")
					.build();
			
			return new PrincipalDetails(userRepository.save(user), oauth2User.getAttributes());
		}
		else {
			// 이전에 구글 회원가입 완료
			return new PrincipalDetails(userEntity, oauth2User.getAttributes());
		}
	}
}
