package com.langyastudio.springboot.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   //2.开启定时任务
public class WebSocketSchedule {
    //添加定时任务
    //或直接指定时间间隔，例如：30秒
    @Scheduled(fixedRate = 30 * 1000)
    public void configureTasks() {
        WsSessionManager.sendAliveMessage();
    }

    //定时检测 WebSocket 的心跳时间跟现在的间隔，超过设定的值说明失去了心跳，就去除他，并更新数据库
    //基于每次 WebSocket 的心跳都更新其心跳时间
    @Scheduled(fixedRate = 30 * 1000)
    public void check() {
        //检查到心跳更新时间大于这么毫秒就认为断开了（心跳时间）
        long                   timeSpan      = 30000;
        //容忍没有心跳次数
        int                    errorTolerant = 10;
        final long             timeSpans     = timeSpan * errorTolerant;

        Map<String, WebSocket> socketMap = WsSessionManager.getAll();
        long                   now       = System.currentTimeMillis();

        if (!socketMap.isEmpty()){
            socketMap.forEach((identifier, webSocket) -> {
                long interval = now - webSocket.getLastHeart();
                if (interval >= timeSpans) {
                    // 内存删除
                    socketMap.remove(identifier);

                    // 失去心跳
                    log.info("{} 失去心跳了", identifier);
                }
            });
        }
    }
}

