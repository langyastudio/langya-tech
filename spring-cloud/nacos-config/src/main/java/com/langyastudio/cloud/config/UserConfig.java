package com.langyastudio.cloud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "user")
public class UserConfig
{
    private int age;

    private String name;

    private String hr;

    private Map<String, Object> map;

    private List<User> users;

    @Override
    public String toString()
    {
        return "UserConfig{" + "age=" + age + ", name='" + name + '\'' + ", map=" + map
                + ", hr='" + hr + '\'' + ", users=" + users + '}';
    }

    @Data
    public static class User
    {

        private String name;

        private String hr;

        private String avg;

        @Override
        public String toString()
        {
            return "User{" + "name='" + name + '\'' + ", hr=" + hr + ", avg=" + avg + '}';
        }
    }
}