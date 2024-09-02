package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SubscribeDto {
	
	/* 사용자Id */
	private int id;
	
	/* 사용자명 */
	private String username;
	
	/* 프로필이미지 url */
	private String profileImageUrl;
	
	/* 구독상태 */
	private Integer subscribeState;	// mariaDB integer
	
	/* 사용자본인여부 */
	private Integer equalUserState;
	
}
