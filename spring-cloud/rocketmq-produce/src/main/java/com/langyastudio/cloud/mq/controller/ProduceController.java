package com.langyastudio.cloud.mq.controller;

import com.langyastudio.cloud.mq.Binding.MySource;
import com.langyastudio.cloud.mq.bean.Foo;
import com.langyastudio.cloud.mq.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author langyastudio
 * @date 2022年01月20日
 */
@RestController
@RequestMapping("/produce")
public class ProduceController
{
    @Autowired
    private SenderService senderService;

    @Autowired
    private MySource mySource;

    @GetMapping("/send")
    public boolean output1() throws Exception
    {
        int index = 1;
        String msgContent = "msg-" + index;

        senderService.send(msgContent);
        senderService.sendWithTags(msgContent, "tagStr");
        return senderService.sendObject(new Foo(index, "foo"), "tagObj");
    }

    @GetMapping("/send3")
    public boolean output3() throws Exception
    {
        // COMMIT_MESSAGE message
        senderService.sendTransactionalMsg("transactional-msg1", 1);
        // ROLLBACK_MESSAGE message
        senderService.sendTransactionalMsg("transactional-msg2", 2);
        // ROLLBACK_MESSAGE message
        senderService.sendTransactionalMsg("transactional-msg3", 3);
        // COMMIT_MESSAGE message
        senderService.sendTransactionalMsg("transactional-msg4", 4);

        int count = 20;
        for (int index = 1; index <= count; index++)
        {
            String msgContent = "pullMsg-" + index;
            mySource.output3()
                    .send(MessageBuilder.withPayload(msgContent).build());
        }

        return true;
    }
}
