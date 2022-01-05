package com.langyastudio.cloud.service.impl;

import com.langyastudio.cloud.service.EchoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 降级熔断
 *
 * fallback class for the specified Feign client interface
 *
 * @author langyastudio
 * @date 2022年01月04日
 */
public class EchoServiceImpl implements EchoService
{
    @Override
    public String notFound()
    {
        return "notFound fallback";
    }

    @Override
    public String sleep()
    {
        return "调用失败，服务被降级";
    }

    @Override
    public String echo(@PathVariable("str") String str)
    {
        return "调用失败，服务被降级";
    }

    @Override
    public String divide(@RequestParam Integer a, @RequestParam Integer b)
    {
        return "调用失败，服务被降级";
    }
}
