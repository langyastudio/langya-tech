package com.langyastudio.cloud.mq.service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.langyastudio.cloud.mq.Binding.MySource;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.support.RocketMQHeaders;

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
    private MySource source;

    public void send(String msg) throws Exception
    {
        source.output1().send(MessageBuilder.withPayload(msg).build());
    }

    public <T> boolean sendWithTags(T msg, String tag) throws Exception
    {
        Message message = MessageBuilder.createMessage(msg,
													   new MessageHeaders(Stream.of(tag).collect(
															   Collectors.toMap(str -> MessageConst.PROPERTY_TAGS, String::toString))));

        return source.output1().send(message);
    }

    public <T> boolean sendObject(T msg, String tag) throws Exception
    {
        Message message = MessageBuilder.withPayload(msg)
                .setHeader(MessageConst.PROPERTY_TAGS, tag)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();

        return source.output1().send(message);
    }

    public <T> boolean sendTransactionalMsg(T msg, int num) throws Exception
    {
        MessageBuilder builder = MessageBuilder.withPayload(msg)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
        builder.setHeader("test", String.valueOf(num));
        builder.setHeader(RocketMQHeaders.TAGS, "binder");

        Message message = builder.build();
        return source.output2().send(message);
    }
}
