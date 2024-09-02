package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	
	/* 댓글 작성 */
	@Transactional
	public Comment writeComment(String content, int imageId, int userId){

		Image image = new Image();
		image.setId(imageId);
		
		User user = userRepository.findById(userId).orElseThrow(()->{
			throw new CustomApiException("Cannat Found User!");
		});
		user.setId(userId);
		
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setImage(image);
		comment.setUser(user);
		
		return commentRepository.save(comment);
	}
	
	/* 댓글 삭제 */
	@Transactional
	public void deleteComment(int id) {
		
		try {
			commentRepository.deleteById(id);
		} catch (Exception e) {
			throw new CustomApiException(e.getMessage());
		}
		
	}

}
