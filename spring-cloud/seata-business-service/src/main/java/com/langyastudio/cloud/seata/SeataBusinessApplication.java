package com.langyastudio.cloud.seata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient(autoRegister = false)
public class SeataBusinessApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SeataBusinessApplication.class, args);
    }

    @FeignClient("storage-service")
    public interface StorageService
    {
        @GetMapping(path = "/storage/{commodityCode}/{count}")
        String storage(@PathVariable("commodityCode") String commodityCode,
                       @PathVariable("count") int count);

    }

    @FeignClient("order-service")
    public interface OrderService
    {
        @PostMapping(path = "/order")
        String order(@RequestParam("userId") String userId,
                     @RequestParam("commodityCode") String commodityCode,
                     @RequestParam("orderCount") int orderCount);

    }
}
