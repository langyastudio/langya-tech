package com.langyastudio.cloud.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.langyastudio.common.constant.AuthConstant;
import com.nimbusds.jose.JWSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Objects;

/**
 * 将登录用户的JWT转化成用户信息的全局过滤器
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered
{
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        //认证信息从Header 或 请求参数 中获取
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String token = serverHttpRequest.getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
        if (Objects.isNull(token))
        {
            token = serverHttpRequest.getQueryParams().getFirst(AuthConstant.JWT_TOKEN_HEADER);
        }

        if (StrUtil.isEmpty(token))
        {
            return chain.filter(exchange);
        }
        try
        {
            //从token中解析用户信息并设置到Header中去
            String    realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String    userStr   = jwsObject.getPayload().toString();

            // 黑名单token(登出、修改密码)校验
            JSONObject jsonObject = JSONUtil.parseObj(userStr);
            String     jti        = jsonObject.getStr("jti");

            Boolean isBlack = redisTemplate.hasKey(AuthConstant.TOKEN_BLACKLIST_PREFIX + jti);
            if (isBlack)
            {

            }

            // 存在token且不是黑名单，request写入JWT的载体信息
            ServerHttpRequest request = serverHttpRequest.mutate().header(AuthConstant.USER_TOKEN_HEADER, userStr).build();
            exchange = exchange.mutate().request(request).build();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder()
    {
        return 0;
    }
}
