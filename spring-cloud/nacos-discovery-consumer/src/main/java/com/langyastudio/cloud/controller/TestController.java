package com.langyastudio.cloud.controller;

import com.langyastudio.cloud.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class TestController
{
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EchoService echoService;

    @Autowired
    private DiscoveryClient discoveryClient;

	//------------------------------------------------------------------------------------------------------------------
	//  RestTemplate
	//------------------------------------------------------------------------------------------------------------------
    @GetMapping("/echo-rest/{str}")
    public String rest(@PathVariable String str)
    {
        return restTemplate.getForObject("http://nacos-discovery-provider/echo/" + str,
                                         String.class);
    }

    @GetMapping("/index")
    public String index()
    {
        return restTemplate.getForObject("http://nacos-discovery-provider", String.class);
    }

    @GetMapping("/test")
    public String test()
    {
        return restTemplate.getForObject("http://nacos-discovery-provider/test", String.class);
    }

    @GetMapping("/sleep")
    public String sleep()
    {
        return restTemplate.getForObject("http://nacos-discovery-provider/sleep", String.class);
    }

	//------------------------------------------------------------------------------------------------------------------
	//  openfeign
	//------------------------------------------------------------------------------------------------------------------
    @GetMapping("/notfound-feign")
    public String notFound()
    {
        return echoService.notFound();
    }

    @GetMapping("/sleep-feign")
    public String sleepFeign()
    {
        return echoService.sleep();
    }


    @GetMapping("/divide-feign")
    public String divide(@RequestParam Integer a, @RequestParam Integer b)
    {
        return echoService.divide(a, b);
    }

    @GetMapping("/echo-feign/{str}")
    public String feign(@PathVariable String str)
    {
        return echoService.echo(str);
    }

	//------------------------------------------------------------------------------------------------------------------
	//  discoveryClient
	//------------------------------------------------------------------------------------------------------------------
	@GetMapping("/services/{service}")
	public Object client(@PathVariable String service)
	{
		return discoveryClient.getInstances(service);
	}

	@GetMapping("/services")
	public Object services()
	{
		return discoveryClient.getServices();
	}
}
