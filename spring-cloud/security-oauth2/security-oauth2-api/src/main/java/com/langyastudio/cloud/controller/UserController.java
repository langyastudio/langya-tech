package com.langyastudio.cloud.controller;

import com.langyastudio.cloud.holder.LoginUserHolder;
import com.langyastudio.common.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取登录用户信息接口
 */
@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private LoginUserHolder loginUserHolder;

    @GetMapping("/currentUser")
    public UserDto currentUser(HttpServletRequest request)
    {
        return loginUserHolder.getCurrentUser(request);
    }

}
