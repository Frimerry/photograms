package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
	 
	/* 본인 프로필 페이지인지 */
	private boolean pageOwnerState;
	
	/* 게시물 수 */
	private int imageCount;
	
	/* 구독상태 */
	private boolean subscribeState;
	/* 구독자 수*/
	private int subscribeCount;
	
	private User user;

}
