package com.langyastudio.springboot.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 目前网上找到的最简单方案就是通过 redis 订阅广播的形式
 * 在本地放个 map 保存请求的 session。也就是说每台服务器都会保存与他连接的 session 于本地
 * 然后发消息的地方要修改，并不是现在这样直接发送，而通过 redis 的订阅机制
 * 服务器要发消息的时候，你通过 redis 广播这条消息，所有订阅的服务端都会收到这个消息，然后本地尝试发送。最后肯定只有有这个对应用户 session 的那台才能发送出去。
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private HttpAuthHandler httpAuthHandler;

    @Autowired
    private MyInterceptor myInterceptor;

    /**
     * 第二个参数 websocket 是你暴露出的 ws 路径
     * addInterceptors 添加握手过滤器
     * setAllowedOrigins("*") 这个是关闭跨域校验
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(httpAuthHandler, "websocket")
                .addInterceptors(myInterceptor)
                .setAllowedOrigins("*");
    }
}
