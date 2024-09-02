package com.cos.photogramstart.domain.comment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "comment_tb")
public class Comment {
	
	/** PK */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// 번호증가전략은 DB를 따라감	
	private int id;
	
	/** 댓글 내용 */
	@Column(length=100, nullable=false)
	private String content;
	
	/** 사용자 정보 */
	@JoinColumn(name="userId")
	@ManyToOne(fetch=FetchType.EAGER)	// 한 댓글에 작성한 유저 정보는 1개만 불러와도 돼서 EAGER
	@JsonIgnoreProperties({"images", "password"})
	private User user;
	
	/** 이미지 정보 */
	@JoinColumn(name="imageId")
	@ManyToOne(fetch=FetchType.EAGER)
	private Image image;
	
	/** 생성일시 */
	private LocalDateTime createDate;
	
	@PrePersist	// DB에 Insert되기직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

}
