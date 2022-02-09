package com.langyastudio.cloud.dubbo.service;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author langyastudio
 * @date 2022年02月09日
 */
@DubboService(version = "1.0.1", group = "lwb")
class EchoServiceImpl implements EchoService
{
    @Override
    public String echo(String message)
    {
        return "[echo] Hello, " + message;
    }
}
