package com.langyastudio.cloud.mq.binding;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author langyastudio
 * @date 2022年01月20日
 */
public interface OutputBinding
{
    @Output("output-common")
    MessageChannel sendCommon();

    @Output("output-tx")
    MessageChannel sendTx();
}
