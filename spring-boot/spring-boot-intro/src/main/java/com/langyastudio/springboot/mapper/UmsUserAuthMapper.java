package com.langyastudio.springboot.mapper;

import com.langyastudio.springboot.model.UmsUserAuth;

public interface UmsUserAuthMapper
{
    int deleteByPrimaryKey(Integer id);

    int insertSelective(UmsUserAuth record);

    UmsUserAuth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UmsUserAuth record);
}