package com.langyastudio.springboot.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 通过 ConcurrentHashMap 来实现了一个 session 池，用来保存已经登录的 web socket 的  session
 */
@Slf4j
public class WsSessionManager {
    /**
     * 保存连接 session 的地方
     */
    private static ConcurrentHashMap<String, WebSocket> SESSION_POOL = new ConcurrentHashMap<>();

    /**
     * 添加 session
     *
     * @param key
     */
    public static void put(String key, WebSocket session) {
        // 添加 session
        SESSION_POOL.put(key, session);
    }

    /**
     * 获得 session
     *
     * @param key
     * @return
     */
    public static WebSocket get(String key) {
        // 获得 session
        return SESSION_POOL.get(key);
    }

    /**
     * 删除 session,会返回删除的 session
     *
     * @param key
     * @return
     */
    public static WebSocket remove(String key) {
        // 删除 session
        return SESSION_POOL.remove(key);
    }

    public static Map<String, WebSocket> getAll() {
        return SESSION_POOL;
    }

    /**
     * 删除并同步关闭连接
     *
     * @param key
     */
    public static void removeAndClose(String key) {
        WebSocket session = remove(key);
        if (session != null) {
            try {
                // 关闭连接
                session.getSession().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendMessage(String key, String message) {
        WebSocket session = get(key);
        if (session != null) {
            try {
                session.getSession().sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendAliveMessage() {
        SESSION_POOL.forEach((token, session) -> {
            try {
                session.getSession().sendMessage(new TextMessage("pong"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
