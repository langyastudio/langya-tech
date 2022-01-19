package com.langyastudio.springboot.service;

import com.langyastudio.springboot.bean.MemberReadHistory;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 浏览记录管理
 *
 * @author jiangjiaxiong
 * @date 2022年01月19日 16:57
 */
public interface MemberReadHistoryService
{
    /**
     * 生成浏览记录
     */
    int create(MemberReadHistory memberReadHistory);

    /**
     * 批量删除浏览记录
     */
    int delete(List<String> ids);

    /**
     * 获取用户浏览历史记录
     */
    Page<MemberReadHistory> list(Long memberId, Integer pageNum, Integer pageSize);
}
