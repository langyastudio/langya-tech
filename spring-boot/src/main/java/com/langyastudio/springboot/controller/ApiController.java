package com.langyastudio.springboot.controller;

import com.langyastudio.springboot.bean.dto.UserParam;
import com.langyastudio.springboot.common.data.EC;
import com.langyastudio.springboot.common.exception.MyException;
import com.langyastudio.springboot.mapper.UmsUserMapper;
import com.langyastudio.springboot.model.UmsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Restful web
 */
@RestController
@RequestMapping("/api")
public class ApiController
{
    @Autowired
    UmsUserMapper umsUserMapper;

    //------------------------------------------------------------------------------------------------------------------
    // Get 请求
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("/users")
    public UmsUser users(@RequestParam(value = "user_name") String userName )
    {
        return umsUserMapper.selectByPrimaryKey(userName);
    }

    @GetMapping("/user/{user_name}")
    public String user(@PathVariable("user_name") String userName)
    {
        return userName;
    }

    //------------------------------------------------------------------------------------------------------------------
    // Post 请求
    //------------------------------------------------------------------------------------------------------------------
    @PostMapping("/user/add")
    public UserParam addUser(@RequestBody UserParam userParam)
    {
        return userParam;
    }

    @PostMapping("/user/addex")
    public Map<String, Object> addUserEx(@RequestBody Map<String, Object> params)
    {
        return params;
    }

    //------------------------------------------------------------------------------------------------------------------
    // Error
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("exception")
    public void exception()
    {
        throw new MyException(EC.ERROR);
    }
}
