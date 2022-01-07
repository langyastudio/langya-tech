package com.langyastudio.cloud.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author langyastudio
 * @date 2022年01月07日
 */
@Configuration
public class RestTemplateConfig
{
    @Bean
    @LoadBalanced
    @SentinelRestTemplate
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
