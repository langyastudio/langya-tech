package com.langyastudio.cloud.mq.service;

import com.langyastudio.cloud.mq.bean.FooMsg;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.ErrorMessage;
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

        // 注意，此处抛出一个 RuntimeException 异常，模拟消费失败
        throw new RuntimeException("我就是故意抛出一个异常");
    }

    @StreamListener("input-tx-1")
    public void receiveTransactionalMsg(String transactionMsg)
    {
        System.out.printf("[onMessage][input-tx-3 线程编号:%s 消息内容：%s] %n", Thread.currentThread().getId(), transactionMsg);
    }

    //------------------------------------------------ error ------------------------------------------------------
    //<destination>.<group>.errors
    //局部错误
    @ServiceActivator(inputChannel = "topic-common-01.group-common-3.errors")
    public void handleError(ErrorMessage errorMessage)
    {
        System.out.printf("[handleError][payload：%s] %n", ExceptionUtils.getRootCauseMessage(errorMessage.getPayload()));
        System.out.printf("[handleError][originalMessage：%s] %n", errorMessage.getOriginalMessage());
        System.out.printf("[handleError][headers：%s] %n", errorMessage.getHeaders());
    }
    // errorChannel
    // 全局错误
    @StreamListener(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
    public void globalHandleError(ErrorMessage errorMessage)
    {
        System.out.printf("[globalHandleError][payload：%s] %n",
                          ExceptionUtils.getRootCauseMessage(errorMessage.getPayload()));
        System.out.printf("[globalHandleError][originalMessage：%s] %n", errorMessage.getOriginalMessage());
        System.out.printf("[globalHandleError][headers：%s] %n", errorMessage.getHeaders());
    }
}
