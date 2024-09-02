package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final OAuth2DetailsService oAuth2DetailsService;
	
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super삭제 - 기존 시큐리티가 가지고 있는 기능 비활성화
		http.csrf().disable();	// csrf토큰 미사용
		http.authorizeRequests()
		.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**").authenticated()	// 인증 필요
		.anyRequest().permitAll()	// 그 외 인증은 허용
		.and()
		.formLogin()
		.loginPage("/auth/signin")
		.loginProcessingUrl("/auth/signin")
		.defaultSuccessUrl("/")
		.and()
		.oauth2Login()
		.userInfoEndpoint()	// oauth2로그인 최종응답으로 회원정보 받기(토큰X)
		.userService(oAuth2DetailsService)
		;
	}
}
