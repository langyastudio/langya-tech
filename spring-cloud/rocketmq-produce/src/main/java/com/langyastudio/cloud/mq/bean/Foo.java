package com.langyastudio.cloud.mq.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Message 消息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Foo
{
    private int id;
    private String bar;
}
