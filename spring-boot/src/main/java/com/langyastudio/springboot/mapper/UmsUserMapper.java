package com.langyastudio.springboot.mapper;

import com.langyastudio.springboot.model.UmsUser;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "db")
public interface UmsUserMapper
{
    String PREFIX= "UmsUserMapper";

    int insertSelective(UmsUser record);

    @CacheEvict(key = "#userName")
    int deleteByPrimaryKey(String userName);

    @CachePut(key = "#record.userName")
    int updateByPrimaryKeySelective(UmsUser record);

    @Cacheable(key = "#userName", unless = "#result == null")
    UmsUser selectByPrimaryKey(String userName);
}