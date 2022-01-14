package com.langyastudio.cloud.service.impl;

import com.langyastudio.cloud.service.EchoService;
import com.langyastudio.common.data.ResultInfo;
import org.springframework.web.bind.annotation.PathVariable;

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
    public ResultInfo echoex(@PathVariable("str") String str)
    {
        return ResultInfo.data("EchoService 服务降级返回");
    }
}
