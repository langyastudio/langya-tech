package com.langyastudio.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * 忽略 NullPointerException 异常的降级
     */
    @RequestMapping("/fallback/{id}")
    @SentinelResource(value = "fallback", fallback = "handleFallback", exceptionsToIgnore =
            {NullPointerException.class})
    public String fallback(@PathVariable String id)
    {
        if (Objects.equals(id, "1"))
        {
            throw new IndexOutOfBoundsException();
        }
        else if (Objects.equals(id, "2"))
        {
            throw new NullPointerException();
        }

        return restTemplate.getForObject("http://nacos-discovery-provider/echo/" + id,
                                         String.class);

        //return restTemplate.getForObject(providerServiceUrl + "/echo/" + id, String.class);
    }

    public ResponseEntity handleFallback(Long id)
    {
        return new ResponseEntity("handleFallback 服务降级返回", HttpStatus.OK);
    }
}
