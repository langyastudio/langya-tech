package com.langyastudio.cloud.middleware;

import java.util.concurrent.Executor;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.StringReader;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Properties;

/**
 * 继承Application接口后项目启动时会按照执行顺序执行run方法
 * 通过设置Order的value来指定执行的顺序 值越小越先执行
 */
@Component
@Log4j2
@Order(value = 1)
@RequiredArgsConstructor
public class ApplicationRunnerStart implements ApplicationRunner
{
    private final ConfigurableApplicationContext context;

    @Autowired
    WebApplicationContext applicationContext;

    @Value("${server.port}")
    Integer port;

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        nacosConfigManager.getConfigService().addListener(
                "nacos-config-dev.yml", "DEFAULT_GROUP", new Listener() {

                    /**
                     * Callback with latest config data.
                     * @param configInfo latest config data for specific dataId in Nacos
                     * server
                     */
                    @Override
                    public void receiveConfigInfo(String configInfo) {
                        Properties properties = new Properties();
                        try {
                            properties.load(new StringReader(configInfo));
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("config changed: " + properties);
                    }

                    @Override
                    public Executor getExecutor() {
                        return null;
                    }
                });

        if (context.isActive())
        {
            String[]    activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
            InetAddress address        = InetAddress.getLocalHost();
            String      url            = String.format("http://%s:%s", address.getHostAddress(), port);

            log.info(" __    ___   _      ___   _     ____ _____  ____ ");
            log.info("/ /`  / / \\ | |\\/| | |_) | |   | |_   | |  | |_  ");
            log.info("\\_\\_, \\_\\_/ |_|  | |_|   |_|__ |_|__  |_|  |_|__ ");
            log.info("                                                      ");
            log.info("运行环境：" + Arrays.toString(activeProfiles));
            log.info("config-系统启动完毕，地址：{}", url);
        }
    }
}

