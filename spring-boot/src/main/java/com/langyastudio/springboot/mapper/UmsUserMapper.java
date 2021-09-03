package com.langyastudio.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.langyastudio.springboot.model.UmsUser;

public interface UmsUserMapper
{
    int deleteByPrimaryKey(Integer id);

    int insertSelective(UmsUser record);

    UmsUser selectByPrimaryKey(String userName);

    int updateByPrimaryKeySelective(UmsUser record);
}