package com.langyastudio.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用程序入口
 */
@SpringBootApplication(scanBasePackages = {"com.langyastudio.springboot"})
public class MongoApplication
{
    public static void main(String[] args)
    {
        SpringApplication app = new SpringApplication(MongoApplication.class);
        app.run(args);
    }
}
