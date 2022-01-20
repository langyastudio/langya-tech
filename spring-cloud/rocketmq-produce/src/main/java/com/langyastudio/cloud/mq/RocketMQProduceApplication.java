package com.langyastudio.cloud.mq;

import com.langyastudio.cloud.mq.Binding.MySource;
import com.langyastudio.cloud.mq.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableBinding({MySource.class})
public class RocketMQProduceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RocketMQProduceApplication.class, args);
    }
}
