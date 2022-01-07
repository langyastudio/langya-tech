package com.langyastudio.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.langyastudio.cloud.handler.CustomBlockHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 限流功能
 */
@RestController
@RequestMapping("/rateLimit")
public class RateLimitController
{
    /**
     * 按资源名称限流，需要指定限流处理逻辑
     */
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public ResponseEntity byResource()
    {
        return new ResponseEntity("按资源名称限流", HttpStatus.OK);
    }

    /**
     * 按URL限流，有默认的限流处理逻辑
     */
    @GetMapping("/byUrl")
    @SentinelResource(value = "byUrl", blockHandler = "handleException")
    public ResponseEntity byUrl()
    {
        return new ResponseEntity("按url限流", HttpStatus.OK);
    }

    /**
     * 自定义通用的限流处理逻辑
     */
    @GetMapping("/customBlockHandler")
    @SentinelResource(value = "customBlockHandler", blockHandler = "handleException",
            blockHandlerClass = CustomBlockHandler.class)
    public ResponseEntity blockHandler()
    {
        return new ResponseEntity("限流成功", HttpStatus.OK);
    }

    public ResponseEntity handleException(BlockException exception)
    {
        return new ResponseEntity(exception.getClass().getCanonicalName(), HttpStatus.OK);
    }

}
