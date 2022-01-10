package com.langyastudio.cloud.config;

import com.langyastudio.cloud.exception.OpenFeignErrorDecoder;
import com.langyastudio.cloud.service.impl.EchoServiceImpl;
import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignConfig
{
    @Bean
    public EchoServiceImpl choServiceFallback()
    {
        return new EchoServiceImpl();
    }

    @Bean
    Logger.Level feignLoggerLevel()
    {
        return Logger.Level.FULL;
    }

    /**
     * 不重试
     */
    @Bean
    Retryer retryer()
    {
        return Retryer.NEVER_RETRY;
    }

    /**
     * 自定义异常解码器
     */
    @Bean
    public ErrorDecoder errorDecoder()
    {
        return new OpenFeignErrorDecoder();
    }
}

