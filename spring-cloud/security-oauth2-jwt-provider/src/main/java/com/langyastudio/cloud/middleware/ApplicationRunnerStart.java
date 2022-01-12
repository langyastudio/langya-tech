package com.langyastudio.cloud.middleware;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

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

    @Value("${server.port}")
    Integer port;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        if (context.isActive())
        {
            InetAddress address        = InetAddress.getLocalHost();
            String      url            = String.format("http://%s:%s", address.getHostAddress(), port);

            log.info(" __    ___   _      ___   _     ____ _____  ____ ");
            log.info("/ /`  / / \\ | |\\/| | |_) | |   | |_   | |  | |_  ");
            log.info("\\_\\_, \\_\\_/ |_|  | |_|   |_|__ |_|__  |_|  |_|__ ");
            log.info("                                                      ");
            log.info("security-oauth2-jwtt-系统启动完毕，地址：{}", url);
        }
    }
}

