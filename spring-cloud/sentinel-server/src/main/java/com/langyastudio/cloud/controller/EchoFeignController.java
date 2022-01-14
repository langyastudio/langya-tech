package com.langyastudio.cloud.controller;

import com.langyastudio.cloud.service.EchoService;
import com.langyastudio.common.data.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 熔断功能
 */
@RestController
@RequestMapping("/feign")
public class EchoFeignController
{
    @Autowired
    private EchoService echoService;

    @GetMapping("/echo/{str}")
    public ResultInfo feign(@PathVariable String str)
    {
        return echoService.echoex(str);
    }
}
