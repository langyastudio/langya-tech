package com.langyastudio.springboot.controller;

import cn.hutool.core.util.URLUtil;
import com.langyastudio.springboot.bean.dto.UserParam;
import com.langyastudio.springboot.common.anno.InValue;
import com.langyastudio.springboot.common.data.EC;
import com.langyastudio.springboot.common.data.validator.UpdateV;
import com.langyastudio.springboot.common.exception.MyException;
import com.langyastudio.springboot.mapper.UmsUserMapper;
import com.langyastudio.springboot.model.SysConfigModel;
import com.langyastudio.springboot.model.UmsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * Restful web
 */
@RestController
@RequestMapping("/api")
@Validated
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

    @GetMapping("/users_ex")
    public UmsUser usersEx(@RequestParam(value = "user_name") String userName )
    {
        return umsUserMapper.selectByPrimaryKeyEx(userName);
    }

    @GetMapping("/users/del")
    public int delUsers(@RequestParam(value = "user_name") String userName )
    {
        return umsUserMapper.deleteByPrimaryKey(userName);
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
    public UserParam addUser(@Validated({UpdateV.class}) @RequestBody UserParam userParam,
                             @Valid @RequestParam(value = "pwd") @Size(min = 6) String pwd,
                             @Valid @RequestParam(value = "type") @InValue({"F", "L"}) String type)
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
        List<SysConfigModel> sysConfigModels =  umsUserMapper.mapperSysConfigListByKey("filterNullStr");
        int rtn = umsUserMapper.mapperSysConfigListByKey2("filterNullStr2");
        throw new MyException(EC.ERROR);
    }

    //------------------------------------------------------------------------------------------------------------------
    // Cookie
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("cookie")
    public void setCookie(HttpServletResponse response){
        // 防止中文乱码问题
        Cookie cookie = new Cookie("key", URLUtil.encode("value"));

        // 不设置时，使用当前域名
        // 设置时，将自动在域名前面加 .
        //cookie.setDomain("test.langyastudio.com");

        //cookie.setPath("/");
        //cookie.setMaxAge(60 * 60 * 24 * days);
        response.addCookie(cookie);
    }
}
