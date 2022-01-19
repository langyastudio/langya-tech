package com.langyastudio.springboot.controller;

import com.langyastudio.springboot.common.data.ResultInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Restful web
 */
@RestController
@RequestMapping("/api")
@Validated
public class ApiController
{
    //------------------------------------------------------------------------------------------------------------------
    // Get 请求
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("/users")
    public ResultInfo users(@RequestParam(value = "user_name") String userName )
    {
        return null;
    }
}
