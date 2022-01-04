package com.langyastudio.cloud.service.impl;

import com.langyastudio.cloud.service.EchoService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
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
    public String echo(@PathVariable("str") String str)
    {
        return "echo fallback";
    }

    @Override
    public String divide(@RequestParam Integer a, @RequestParam Integer b)
    {
        return "divide fallback";
    }
}
