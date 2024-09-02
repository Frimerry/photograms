package com.cos.photogramstart.web;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.constant.constant;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.comment.CommentDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
	
	private final CommentService commentService;
	
	/* 댓글 작성 */
	@PostMapping("/api/comment")
	public ResponseEntity<?> writeComment(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult,
			@AuthenticationPrincipal PrincipalDetails principalDetails){
		
		Comment comment = commentService.writeComment(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(constant.POSITIVE, "Comment successfully written!", comment), HttpStatus.OK);
	}
	
	/* 댓글 삭제*/
	@DeleteMapping("/api/comment/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable int id){
		
		commentService.deleteComment(id);
		
		return new ResponseEntity<>(new CMRespDto<>(constant.POSITIVE, "Comment successfully deleted!", null), HttpStatus.OK);
	}

}
