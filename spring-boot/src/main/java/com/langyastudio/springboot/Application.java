package com.langyastudio.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 应用程序入口
 */
@SpringBootApplication(scanBasePackages = {"com.langyastudio.springboot"})
@EnableScheduling
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication app = new SpringApplication(Application.class);

        //app.setBannerMode(Banner.Mode.OFF);
        //app.addListeners(new MyListener());

        app.run(args);
    }
}
