package com.cos.photogramstart.domain.subscribe;

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

import com.cos.photogramstart.domain.user.User;

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
@Table(name = "subscribe_tb", uniqueConstraints = {
		@UniqueConstraint(name="subscribe_uk", columnNames = { "fromUserId", "toUserId" })
		}) // 중복구독 필요없음
public class Subscribe {
	
	/** PK */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// 번호증가전략은 DB를 따라감	
	private int id;
	
	/** 구독하는 사용자Id */
	@JoinColumn(name="fromUserId")
	@ManyToOne
	private User fromUser;
	
	/** 구독받는 사용자Id */
	@JoinColumn(name="toUserId")
	@ManyToOne
	private User toUser;
	
	/** 생성일시 */
	private LocalDateTime createDate;
	
	@PrePersist	// DB에 Insert되기직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
