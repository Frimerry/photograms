package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
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
@Table(name = "image_tb")
public class Image {
	
	/** PK */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// 번호증가전략은 DB를 따라감	
	private int id;
	
	/** 이미지 설명 */
	private String caption;
	
	/** 이미지 저장경로 */
	private String postImageUrl;
	
	/** 이미지 게시자 정보 */
	@JsonIgnoreProperties({"images", "password"})
	@JoinColumn(name="userId")
	@ManyToOne
	private User user;
	
	/** 좋아요 */
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy="image")
	private List<Likes> likes;
	
	@Transient	// DB컬럼생성X
	private boolean likeState;
	
	@Transient
	private int likeCount;
	
	/** 댓글 */
	@OrderBy("id DESC")	// 최신순
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy="image")	// 관계에서 외래키
	List<Comment> comments;
	
	/** 생성일시 */
	private LocalDateTime createDate;
	
	@PrePersist	// DB에 Insert되기직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

}
