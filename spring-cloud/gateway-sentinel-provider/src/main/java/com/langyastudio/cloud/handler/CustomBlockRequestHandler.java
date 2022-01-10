package com.langyastudio.cloud.handler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.http.MediaType;

@Component
public class CustomBlockRequestHandler implements BlockRequestHandler
{
    private static final String DEFAULT_BLOCK_MSG_PREFIX = "This request is blocked by guoxiuzhi's sentinel.";

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable ex)
    {
        String bodyJson = "{\n" +
                "  \"code\": 429,\n" +
                "  \"data\": \"%s The detail exception class: %s \"\n" +
                "}";

        return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .contentType(MediaType.TEXT_PLAIN)
                .bodyValue(String.format(bodyJson, DEFAULT_BLOCK_MSG_PREFIX, ex.getClass().getSimpleName()));
    }
}
