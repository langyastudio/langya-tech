package com.langyastudio.cloud.service;

import com.langyastudio.cloud.config.FeignConfig;
import com.langyastudio.cloud.service.impl.EchoServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用nacos注册的服务
 *
 * @author jiangjiaxiong
 * @date 2022年01月04日 14:04
 */
@FeignClient(name = "${service-url.nacos-discovery-provider}", fallback = EchoServiceImpl.class,
        configuration = FeignConfig.class)
public interface EchoService
{
    @GetMapping("/notfound")
    String notFound();

    @GetMapping("/sleep")
    String sleep();

    @GetMapping("/echo/{str}")
    String echo(@PathVariable("str") String str);

    @GetMapping("/divide")
    String divide(@RequestParam("a") Integer a, @RequestParam("b") Integer b);
}