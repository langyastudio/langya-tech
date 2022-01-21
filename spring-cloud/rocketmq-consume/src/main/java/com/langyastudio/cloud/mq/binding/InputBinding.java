package com.langyastudio.cloud.mq.binding;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author jiangjiaxiong
 * @date 2022年01月21日 9:34
 */
public interface InputBinding
{
    @Input("input-common-1")
    SubscribableChannel inputCommon1();

    @Input("input-common-2")
    SubscribableChannel inputCommon2();

    @Input("input-common-3")
    SubscribableChannel inputCommon3();

    @Input("input-tx-1")
    SubscribableChannel inputTx1();
}
