package com.langyastudio.springboot.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocket implements java.io.Serializable {

    /**
     * 代表一个连接
     */
    private WebSocketSession session;

    /**
     * 最后心跳时间
     */
    private Long lastHeart;
}
