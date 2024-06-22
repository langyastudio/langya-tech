package com.langyastudio.springboot.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   //2.开启定时任务
public class WebSocketSchedule {
    //添加定时任务
    //或直接指定时间间隔，例如：30秒
    @Scheduled(fixedRate=30*1000)
    public void configureTasks(){
        WsSessionManager.sendAliveMessage();
    }
}

