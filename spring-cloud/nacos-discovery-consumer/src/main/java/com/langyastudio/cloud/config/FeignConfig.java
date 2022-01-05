package com.langyastudio.cloud.config;

import com.langyastudio.cloud.service.impl.EchoServiceImpl;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig
{
    @Bean
    Logger.Level feignLoggerLevel()
    {
        return Logger.Level.FULL;
    }

    @Bean
    public EchoServiceImpl choServiceFallback() {
        return new EchoServiceImpl();
    }
}

