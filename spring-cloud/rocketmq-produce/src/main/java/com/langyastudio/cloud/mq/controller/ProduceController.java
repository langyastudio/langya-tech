package com.langyastudio.cloud.mq.controller;

import com.langyastudio.cloud.mq.bean.FooMsg;
import com.langyastudio.cloud.mq.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

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

    @GetMapping("/send1")
    public boolean output1(@RequestParam("msg") String msg) throws Exception
    {
        int msgId = new SecureRandom().nextInt();
        return senderService.sendObject(new FooMsg(msgId, msg), "tagObj", 0);
    }

    @GetMapping("/send3")
    public boolean output3() throws Exception
    {
        // unknown message
        senderService.sendTransactionalMsg("transactional-msg1", 1);
        // rollback message
        senderService.sendTransactionalMsg("transactional-msg2", 2);
        // commit message
        senderService.sendTransactionalMsg("transactional-msg3", 3);

        return true;
    }
}
