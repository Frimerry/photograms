package com.cos.photogramstart.web.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CommentDto {
	
	/* 댓글 내용 */
	@NotBlank(message="댓글 내용을 작성해주세요.")
	private String content;
	
	/* 이미지 id */
	@NotNull
	private Integer imageId;
	
	// toEntity불필요

}
