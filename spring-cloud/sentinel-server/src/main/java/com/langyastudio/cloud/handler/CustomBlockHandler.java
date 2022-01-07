package com.langyastudio.cloud.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomBlockHandler
{
    public ResponseEntity handleException(BlockException exception)
    {
        return new ResponseEntity("自定义限流信息", HttpStatus.OK);
    }
}