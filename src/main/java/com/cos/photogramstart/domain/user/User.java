package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.cos.photogramstart.domain.image.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA - Java Persistence API(자바로 데이터를 영구적으로 저장할 수 있는 API 제공)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user_tb")
public class User {
	
	/** PK */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// 번호증가전략은 DB를 따라감	
	private int id;
	
	/** 사용자Id */
	@Column(length=200, unique=true)
	private String username;
	
	/** 비밀번호 */
	@Column(nullable=false)
	private String password;
	
	/** 이름 */
	@Column(nullable=false)
	private String name;
	
	/** 웹사이트 */
	private String website;
	
	/** 바이오(자기소개) */
	private String bio;
	
	/** 이메일 */
	@Column(nullable=false)
	private String email;
	
	/** 전화번호 */
	private String phone;
	
	/** 성별 */
	private String gender;
	
	/** 사진 url */
	private String profileImageUrl;
	
	/** 역할권한 */
	private String role;
	
	/** 업로드이미지 */
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)	// 연관관계의 주인이 아님, 테이블 컬럼생성 X
	private List<Image> images; 
	
	/** 생성일시 */
	private LocalDateTime createDate;
	
	@PrePersist	// DB에 Insert되기직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", website="
				+ website + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", gender=" + gender
				+ ", profileImageUrl=" + profileImageUrl + ", role=" + role  + ", createDate="
				+ createDate + "]";
	}
	
}
