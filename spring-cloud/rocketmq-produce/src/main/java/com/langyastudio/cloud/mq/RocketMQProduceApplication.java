package com.langyastudio.cloud.mq;

import com.langyastudio.cloud.mq.binding.OutputBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;


@SpringBootApplication
@EnableBinding({OutputBinding.class})
public class RocketMQProduceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RocketMQProduceApplication.class, args);
    }
}