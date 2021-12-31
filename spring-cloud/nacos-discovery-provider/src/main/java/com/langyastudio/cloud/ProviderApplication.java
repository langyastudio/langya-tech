package com.langyastudio.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//开启服务注册与发现功能
@EnableDiscoveryClient
public class ProviderApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ProviderApplication.class, args);
    }
}