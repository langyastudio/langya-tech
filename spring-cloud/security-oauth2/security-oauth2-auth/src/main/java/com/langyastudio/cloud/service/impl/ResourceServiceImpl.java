package com.langyastudio.cloud.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.langyastudio.common.constant.AuthConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 资源与角色匹配关系管理业务类
 * <p>
 * 初始化的时候把资源与角色匹配关系缓存到Redis中，方便网关服务进行鉴权的时候获取
 *
 * @author langyastudio
 * @date 2022年01月14日
 */
@Service
public class ResourceServiceImpl
{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private Map<String, List<String>> resourceRolesMap;

    @PostConstruct
    public void initData()
    {
        resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("/admin/hello", CollUtil.toList("ADMIN"));
        resourceRolesMap.put("/admin/user/currentUser", CollUtil.toList("ADMIN", "TEST"));
        redisTemplate.opsForHash().putAll(AuthConstant.RESOURCE_ROLES_MAP_KEY, resourceRolesMap);
    }
}
