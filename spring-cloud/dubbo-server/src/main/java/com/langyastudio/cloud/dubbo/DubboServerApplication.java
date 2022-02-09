package com.langyastudio.cloud.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Dubbo Spring Cloud Server Bootstrap.
 */
@EnableDiscoveryClient
@EnableAutoConfiguration
public class DubboServerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DubboServerApplication.class);
    }
}

