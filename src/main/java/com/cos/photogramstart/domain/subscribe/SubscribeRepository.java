package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer>{
	
	/* 구독하기 */
	@Modifying	// INSERT, DELETE, UPDATE를 nativeQuery로 작성 시 필요
	@Query(value ="INSERT INTO subscribe_tb(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
	void mSubscribe(int fromUserId, int toUserId);
	
	/* 구독취소 */
	@Modifying
	@Query(value ="DELETE FROM subscribe_tb WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
	void mUnSubscibe(int fromUserId, int toUserId);
	
	/* 구독상태 */
	@Query(value="SELECT COUNT(*) FROM subscribe_tb WHERE fromUserId =:principalId AND toUserId =:pageUserId", nativeQuery = true)
	int mSubscribeState(int principalId, int pageUserId);
	
	/* 구독수 */
	@Query(value="SELECT COUNT(*) FROM subscribe_tb WHERE fromUserId =:pageUserId", nativeQuery = true)
	int mSubscribeCount(int pageUserId);

}
