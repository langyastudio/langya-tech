package com.alibaba.cloud.dubbo.bootstrap;

import com.langyastudio.cloud.dubbo.service.EchoService;
import org.apache.dubbo.config.annotation.DubboReference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dubbo Spring Cloud Client Bootstrap.
 */
@EnableDiscoveryClient
@EnableAutoConfiguration
@RestController
public class DubboClientApplication
{
    @DubboReference(version = "1.0.1", group = "lwb")
    private EchoService echoService;

    @GetMapping("/echo")
    public String echo(String message)
    {
        return echoService.echo(message);
    }

    public static void main(String[] args)
    {
        SpringApplication.run(DubboClientApplication.class);
    }
}
