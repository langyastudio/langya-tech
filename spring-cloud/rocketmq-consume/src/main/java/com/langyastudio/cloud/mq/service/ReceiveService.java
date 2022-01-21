package com.langyastudio.cloud.mq.service;

import com.langyastudio.cloud.mq.bean.FooMsg;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ReceiveService
{
    @StreamListener("input1")
    public void receiveInput1(String receiveMsg)
    {
        System.out.println("input1 receive: " + receiveMsg);
    }

    @StreamListener("input2")
    public void receiveInput2(String receiveMsg)
    {
        System.out.println("input2 receive: " + receiveMsg);
    }

    @StreamListener("input3")
    public void receiveInput3(@Payload FooMsg foo)
    {
        System.out.println("input3 receive: " + foo);
    }

    @StreamListener("input4")
    public void receiveTransactionalMsg(String transactionMsg)
    {
        System.out.println("input4 receive transaction msg: " + transactionMsg);
    }

}
