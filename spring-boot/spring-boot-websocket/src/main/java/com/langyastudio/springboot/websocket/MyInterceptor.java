package com.langyastudio.springboot.websocket;

import cn.hutool.core.util.StrUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 通过实现 HandshakeInterceptor 接口来定义握手拦截器
 * 这里是建立握手时的事件，分为握手前与握手后，而 Handler 的事件是在握手成功后的基础上建立 socket 的连接
 * 所以在如果把认证放在这个步骤相对来说最节省服务器资源。它主要有两个方法 beforeHandshake 与 afterHandshake ，顾名思义一个在握手前触发，一个在握手后触发。
 */
@Component
public class MyInterceptor implements HandshakeInterceptor {

    /**
     * 握手前
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     *
     * @return
     *
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler
            , Map<String, Object> attributes) throws Exception {
        System.out.println("握手开始");

        // 从请求头Header中获取sec-websocket-protocol的值，用于获取用户的token
        // 再通过token解析出用户名信息
        List<String> token = request.getHeaders().get("sec-websocket-protocol");
        if(null == token){
            token = new LinkedList<>();
            token.add("jwttoken");
        }

        if (null != token){
            String uid = token.get(0); //业务上改为通过token获取用户名
            if (StrUtil.isNotBlank(uid)) {
                // 放入属性域
                attributes.put("token", uid);

                System.out.println("用户 token " + uid + " 握手成功！");
                return true;
            }
        }

        System.out.println("用户登录已失效");
        return false;
    }

    /**
     * 握手后
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param exception
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {
        System.out.println("握手完成");
    }
}

