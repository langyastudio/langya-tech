package com.langyastudio.cloud.mq.service;

import com.langyastudio.cloud.mq.binding.OutputBinding;
import org.apache.rocketmq.common.message.MessageConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;


@Service
public class SenderService
{
    @Autowired
    private OutputBinding source;

    /**
     * 发送消息
     *
     * @param msg 消息内容
     * @param tag 标签
     * @param delay 设置延迟级别为x秒后消费
     * @return
     * @throws Exception
     */
    public <T> boolean sendObject(T msg, String tag, Integer delay) throws Exception
    {
        Message<T> message = MessageBuilder.withPayload(msg)
                .setHeader(MessageConst.PROPERTY_TAGS, tag)
                .setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, delay)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();

        return source.sendCommon().send(message);
    }

    public <T> boolean sendTransactionalMsg(T msg, int num) throws Exception
    {
        Message<T> message  = MessageBuilder.withPayload(msg)
                .setHeader("tx-state", String.valueOf(num))
                .setHeader(MessageConst.PROPERTY_TAGS, "binder")
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();

        return source.sendTx().send(message);
    }
}
