package com.langyastudio.cloud.service;

import cn.hutool.core.collection.CollUtil;
import com.langyastudio.common.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UmsAdminService
{
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto loadUserByUsername(String username)
    {
        String  password = passwordEncoder.encode("123456");
        if("admin".equals(username))
        {
            return new UserDto(1L, "admin", password, 1, "", CollUtil.toList("ADMIN"));
        }
        else if("langya".equals(username))
        {
            return new UserDto(1L, "admin", password, 1, "", CollUtil.toList("ADMIN", "TEST"));
        }

        return null;
    }
}
