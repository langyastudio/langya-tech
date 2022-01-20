package com.langyastudio.springboot.repository;

import com.langyastudio.springboot.bean.MemberReadHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 商品浏览历史
 *
 * @author langyastudio
 * @date 2022年01月19日
 */
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory, String>
{
    /**
     * 根据会员id按时间倒序获取浏览记录
     *
     * @param memberId 会员id
     */
    Page<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId, Pageable pageable);
    void deleteAllByMemberId(Long memberId);

    MemberReadHistory findByMemberIdAndProductId(Long memberId, Long productId);
    int deleteByMemberIdAndProductId(Long memberId, Long productId);
}
