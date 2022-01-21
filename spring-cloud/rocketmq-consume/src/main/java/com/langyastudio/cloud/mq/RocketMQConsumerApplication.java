package com.langyastudio.cloud.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.xml.sax.InputSource;

@SpringBootApplication
@EnableBinding({InputSource.class})
public class RocketMQConsumerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RocketMQConsumerApplication.class, args);
    }
}
