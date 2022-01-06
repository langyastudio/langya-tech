package com.langyastudio.cloud.config;

import com.langyastudio.cloud.exception.OpenFeignErrorDecoder;
import com.langyastudio.cloud.service.impl.EchoServiceImpl;
import feign.Logger;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class FeignConfig
{
    @Bean
    public EchoServiceImpl choServiceFallback()
    {
        return new EchoServiceImpl();
    }

    @Bean
    Logger.Level feignLoggerLevel()
    {
        return Logger.Level.FULL;
    }

    /**
     * 不重试
     */
    @Bean
    Retryer retryer()
    {
        return Retryer.NEVER_RETRY;
    }

    /**
     * 自定义异常解码器
     */
    @Bean
    public ErrorDecoder errorDecoder()
    {
        return new OpenFeignErrorDecoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor()
    {
        return requestTemplate -> {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (null != attributes)
            {
                HttpServletRequest  request = attributes.getRequest();
                Map<String, String> headers = getRequestHeaders(request);

                // 传递所有请求头,防止部分丢失
                //此处也可以只传递认证的header
                requestTemplate.header("Authorization", request.getHeader("Authorization"));
                for (Map.Entry<String, String> entry : headers.entrySet())
                {
                    requestTemplate.header(entry.getKey(), entry.getValue());
                }
            }
        };
    }

    /**
     * 获取请求头
     */
    private Map<String, String> getRequestHeaders(HttpServletRequest request)
    {
        Map<String, String> map         = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if (enumeration != null)
        {
            while (enumeration.hasMoreElements())
            {
                String key   = enumeration.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);
            }
        }
        return map;
    }
}

