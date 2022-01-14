package com.langyastudio.cloud.component;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.langyastudio.common.data.EC;
import com.langyastudio.common.data.ResultInfo;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;


/**
 * 自定义返回结果：没有权限访问时
 */
@Component
public class RestfulAccessDeniedHandler implements ServerAccessDeniedHandler
{
    private static final Pattern CALLBACK_PARAM_PATTERN = Pattern.compile("[0-9A-Za-z_\\.]*");

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied)
    {
        ServerHttpResponse response    = exchange.getResponse();
        ServerHttpRequest  request     = exchange.getRequest();
        HttpHeaders        httpHeaders = response.getHeaders();

        response.setStatusCode(HttpStatus.OK);
        ResultInfo resultInfo = ResultInfo.data(EC.ERROR_USER_FORBIDDEN, denied.getMessage());
        String     body   = JSON.toJSONString(resultInfo);

        //兼容jsonp请求
        String functionName = request.getQueryParams().getFirst("callback");
        if (functionName != null && this.isValidJsonpQueryParam(functionName))
        {
            String text      = JSON.toJSONString(resultInfo);
            String jsonpText = functionName + "(" + text + ")";

            //jsonp content
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/javascript");
            body = jsonpText;
        }
        else
        {
            // 跨域
            String uiDomain = null;
            String origin   = request.getQueryParams().getFirst("origin");
            if (CharSequenceUtil.isNotBlank(origin))
            {
                uiDomain = origin;
            }

            if (CharSequenceUtil.isNotBlank(uiDomain))
            {
                if (request.getMethod() == HttpMethod.OPTIONS || request.getMethod() == HttpMethod.POST)
                {
                    httpHeaders.setAccessControlAllowOrigin(uiDomain);
                    httpHeaders.setAccessControlAllowCredentials(true);

                    // 在这个时间范围内，所有同类型的请求都将不再发送预检请求而是直接使用此次返回的头作为判断依据
                    httpHeaders.setAccessControlMaxAge(1440);
                }
            }

            //返回数据
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        }

        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    protected boolean isValidJsonpQueryParam(String value)
    {
        return CALLBACK_PARAM_PATTERN.matcher(value).matches();
    }
}
