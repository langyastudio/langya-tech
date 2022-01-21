package com.langyastudio.cloud.mq.listener;

import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;

import org.springframework.messaging.Message;


@Log4j2
@RocketMQTransactionListener(txProducerGroup = "group-tx", corePoolSize = 5,
        maximumPoolSize = 10)
public class TransactionListenerImpl implements RocketMQLocalTransactionListener
{
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg)
    {
        Object num = msg.getHeaders().get("tx-state");

        // ... local transaction process, return rollback, commit or unknown

        if ("1".equals(num))
        {
            log.info("executer: " + new String((byte[]) msg.getPayload()) + " unknown");
            return RocketMQLocalTransactionState.UNKNOWN;
        }
        else if ("2".equals(num))
        {
            log.info("executer: " + new String((byte[]) msg.getPayload()) + " rollback");
            return RocketMQLocalTransactionState.ROLLBACK;
        }

        log.info("executer: " + new String((byte[]) msg.getPayload()) + " commit");
        return RocketMQLocalTransactionState.COMMIT;
    }

    // 在事务消息长事件未被提交或回滚时
    // RocketMQ 会回查事务消息对应的生产者分组下的 Producer 获得事务消息的状态。此时，该方法就会被调用
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg)
    {
        // ... check local transaction status and return rollback, commit or unknown
        log.info("check: " + new String((byte[]) msg.getPayload()));
        return RocketMQLocalTransactionState.COMMIT;
    }
}
