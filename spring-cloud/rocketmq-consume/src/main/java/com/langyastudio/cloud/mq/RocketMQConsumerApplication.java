package com.langyastudio.cloud.mq;

import com.langyastudio.cloud.mq.binding.InputBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding({InputBinding.class})
public class RocketMQConsumerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RocketMQConsumerApplication.class, args);
    }
}
