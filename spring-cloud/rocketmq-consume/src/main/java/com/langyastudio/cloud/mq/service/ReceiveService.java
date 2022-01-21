package com.langyastudio.cloud.mq.service;

import com.langyastudio.cloud.mq.bean.FooMsg;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ReceiveService
{
    @StreamListener("input-common-1")
    public void receiveInput1(@Payload FooMsg receiveMsg)
    {
        System.out.printf("[onMessage][input-common-1 线程编号:%s 消息内容：%s] %n", Thread.currentThread().getId(), receiveMsg);
    }

    @StreamListener("input-common-2")
    public void receiveInput2(@Payload FooMsg receiveMsg)
    {
        System.out.printf("[onMessage][input-common-2 线程编号:%s 消息内容：%s] %n", Thread.currentThread().getId(), receiveMsg);
    }

    @StreamListener("input-common-3")
    public void receiveInput3(@Payload FooMsg receiveMsg)
    {
        System.out.printf("[onMessage][input-common-3 线程编号:%s 消息内容：%s] %n", Thread.currentThread().getId(), receiveMsg);
    }

    @StreamListener("input-tx-1")
    public void receiveTransactionalMsg(String transactionMsg)
    {
        System.out.printf("[onMessage][input-tx-3 线程编号:%s 消息内容：%s] %n", Thread.currentThread().getId(), transactionMsg);
    }

}
