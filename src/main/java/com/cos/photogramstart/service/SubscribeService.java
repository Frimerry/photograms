package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em;	// Repository는 EntityManager를 구현해서 만들어져있는 구현체
	
	/* 구독목록 */
	@Transactional(readOnly=true)
	public List<SubscribeDto> subscriptionList(int principalId, int pageUserId){
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		sb.append("if((SELECT TRUE FROM subscribe WHERE fromUserId = ? AND toUSerId = u.id), 1, 0) subscribeState, ");
		sb.append("if((u.id = ?), 1, 0) equalUserState ");
		sb.append("FROM user u INNER JOIN subscribe s ");
		sb.append("ON u.id= s.toUserId ");
		sb.append("WHERE s.fromUserId = ?");
		
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
		
		// qlrm(Query Langugage Result Mapper) 라이브러리: DB 조회 결과를 Java 클래스에 매핑
		JpaResultMapper result = new JpaResultMapper();
		
		return result.list(query, SubscribeDto.class);
	}
	
	/* 구독하기 */
	@Transactional
	public void subscribe(int fromUserId, int toUserId) {
		
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		} catch (Exception e) {
			throw new CustomApiException("이미 구독중입니다.");
		}
	}
	
	/* 구독취소 */
	@Transactional
	public void unSubscribe(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscibe(fromUserId, toUserId);
	}

}
