package com.langyastudio.springboot.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用程序入口
 */
@SpringBootApplication(scanBasePackages = {"com.langyastudio.springboot"})
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }
}
