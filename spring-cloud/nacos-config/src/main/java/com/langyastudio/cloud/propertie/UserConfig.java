package com.langyastudio.cloud.propertie;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "user")
public class UserConfig
{
    private int age;

    private String name;

    private Map<String, Object> map;

    @Override
    public String toString()
    {
        return "UserConfig{" + "age=" + age + ", name='" + name + '\'' + ", map=" + map;
    }
}