package com.langyastudio.springboot.service.impl;

import com.langyastudio.springboot.bean.MemberReadHistory;
import com.langyastudio.springboot.repository.MemberReadHistoryRepository;
import com.langyastudio.springboot.service.MemberReadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 浏览记录管理Service实现类
 *
 * @author langyastudio
 * @date 2022年01月19日
 */
@Service
public class MemberReadHistoryServiceImpl implements MemberReadHistoryService
{
    @Autowired
    private MemberReadHistoryRepository memberReadHistoryRepository;

    @Override
    public int create(MemberReadHistory memberReadHistory)
    {
        memberReadHistory.setId(null);
        memberReadHistory.setCreateTime(new Date());
        memberReadHistoryRepository.save(memberReadHistory);

        return 1;
    }

    @Override
    public int delete(List<String> ids)
    {
        List<MemberReadHistory> deleteList = new ArrayList<>();
        for (String id : ids)
        {
            MemberReadHistory memberReadHistory = new MemberReadHistory();
            memberReadHistory.setId(id);
            deleteList.add(memberReadHistory);
        }
        memberReadHistoryRepository.deleteAll(deleteList);

        return ids.size();
    }

    @Override
    public Page<MemberReadHistory> list(Long memberId, Integer pageNum, Integer pageSize)
    {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);

        return memberReadHistoryRepository.findByMemberIdOrderByCreateTimeDesc(memberId, pageable);
    }
}
