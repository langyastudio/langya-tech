package com.langyastudio.springboot.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.EOFException;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 通过继承 TextWebSocketHandler 类并覆盖相应方法，可以对 websocket 的事件进行处理
 */
@Slf4j
@Component
public class HttpAuthHandler extends TextWebSocketHandler {

    /**
     * socket 建立成功事件
     * <p>
     * 同原生注解里的 @OnOpen 功能
     *
     * @param session
     *
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Object token = session.getAttributes().get("token");
        if (token != null) {
            // 用户连接成功，放入在线用户缓存
            WsSessionManager.add(token.toString(), session);
        } else {
            throw new RuntimeException("用户登录已经失效!");
        }

        session.sendMessage(new TextMessage("ping" + LocalDateTime.now().toString()));
    }

    /**
     * 接收消息事件
     * <p>
     * 同原生注解里的 @OnMessage 功能
     *
     * @param session
     * @param message
     *
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获得客户端传来的消息
        String payload = message.getPayload();
        Object token   = session.getAttributes().get("token");
        System.out.println("server 接收到 " + token + " 发送的 " + payload);

        session.sendMessage(new TextMessage("server 发送给 " + token + " 消息 " + payload + " " + LocalDateTime.now().toString()));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if ((exception instanceof EOFException) && exception.getCause() == null) {
            log.warn("客户端异常退出：{}", session.getId());
            log.error("客户端异常退出", exception);
        } else {
            log.error("socket发生异常：{}", session.getId());
            log.error("异常信息", exception);
        }

        try {
            session.close();
        } catch (IOException e) {
            log.error("关闭socket发生异常", e);
        }
    }

    /**
     * socket 断开连接时
     * <p>
     * 同原生注解里的 @OnClose 功能
     *
     * @param session
     * @param status
     *
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object token = session.getAttributes().get("token");
        if (token != null) {
            // 用户退出，移除缓存
            WsSessionManager.remove(token.toString());
        }
    }
}
