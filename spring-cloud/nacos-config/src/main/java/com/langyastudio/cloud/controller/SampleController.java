package com.langyastudio.cloud.controller;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.langyastudio.cloud.propertie.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author langyastudio
 * @date 2022年01月04日
 */
@RestController
@RefreshScope
class SampleController
{
    @Autowired
    UserConfig userConfig;

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Autowired
    private Environment environment;

    @Value("${user.name:zz}")
    String userName;

    @Value("${user.age:25}")
    Integer age;

    @RequestMapping("/user")
    public String simple()
    {
        return "Hello Nacos Config!" + "Hello " + userName + " " + age + " [UserConfig]: "
                + userConfig + "!" + nacosConfigManager.getConfigService();
    }

    @RequestMapping("/get/{name}")
    public String getValue(@PathVariable String name)
    {
        return String.valueOf(environment.getProperty(name));
    }

    @RequestMapping("/bool")
    public boolean bool()
    {
        return (Boolean) (userConfig.getMap().get("location"));
    }
}