package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(name = "likes_tb", uniqueConstraints = {
		@UniqueConstraint(name="likes_uk", columnNames = { "imageId", "userId" })
		}) // 좋아요를 이미 한 이미지에 중복 좋아요 불가
public class Likes {
	
	/** PK */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// 번호증가전략은 DB를 따라감	
	private int id;
	
	/** 사진정보 */
	@JoinColumn(name="imageId")
	@ManyToOne
	private Image image;
	
	/** 사용자 정보 */
	@JsonIgnoreProperties({"images", "password"})
	@JoinColumn(name="userId")
	@ManyToOne
	private User user;
	
	/** 생성일시 */
	private LocalDateTime createDate;
	
	@PrePersist	// DB에 Insert되기직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

}
