package com.langyastudio.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import data.ResultInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * 熔断功能
 */
@Log4j2
@RestController
@RequestMapping("/breaker")
public class CircleBreakerController
{
    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-discovery-provider}")
    private String providerServiceUrl;

    @RequestMapping("/fallback/{id}")
    @SentinelResource(value = "fallback", fallback = "handleFallback")
    public ResultInfo fallback(@PathVariable String id)
    {
        return restTemplate.getForObject(providerServiceUrl + "/echoex/" + id, ResultInfo.class);
    }

    /**
     * 忽略 NullPointerException 异常的降级
     */
    @RequestMapping("/fallbackex/{id}")
    @SentinelResource(value = "fallbackex", fallback = "handleFallback", exceptionsToIgnore =
            {NullPointerException.class})
    public ResultInfo fallbackEx(@PathVariable String id)
    {
        if (Objects.equals(id, "1"))
        {
            throw new NullPointerException();
        }

        return restTemplate.getForObject(providerServiceUrl + "/echoex/" + id, ResultInfo.class);
    }

    /**
     * 熔断函数，需要与接口的参数匹配 or use fallbackClass
     */
    public ResultInfo handleFallback(String id)
    {
        return ResultInfo.data("handleFallback 服务降级返回");
    }
}
