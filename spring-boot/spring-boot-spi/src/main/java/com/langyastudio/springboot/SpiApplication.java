package com.langyastudio.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * 应用程序入口
 */
@SpringBootApplication(scanBasePackages = {"com.langyastudio.springboot"})
public class SpiApplication
{
    public static void main(String[] args)
    {
        List<IAnimalSay> dataBaseSPIs = SpringFactoriesLoader.loadFactories(IAnimalSay.class,
                                                                            Thread.currentThread().getContextClassLoader());

        for(IAnimalSay datBaseSPI:dataBaseSPIs){
            datBaseSPI.say();
        }

        //SpringApplication app = new SpringApplication(SpiApplication.class);
        //app.run(args);
    }
}
