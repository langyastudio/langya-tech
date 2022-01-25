package com.langyastudio.cloud.seata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 入口
 */
@EnableFeignClients
@SpringBootApplication
public class SeataOrderApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SeataOrderApplication.class, args);
    }

    @FeignClient("account-service")
    public interface AccountService
    {
        @GetMapping(path = "/account")
        String account(@RequestParam("userId") String userId,
                       @RequestParam("money") int money);

    }
}
