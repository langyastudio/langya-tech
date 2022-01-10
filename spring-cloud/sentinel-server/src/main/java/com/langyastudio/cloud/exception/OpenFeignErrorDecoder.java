package com.langyastudio.cloud.exception;

import com.alibaba.fastjson.JSON;
import data.EC;
import data.ResultInfo;
import exception.MyException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author langyastudio
 * @date 2022年01月06日
 */
@Log4j2
public class OpenFeignErrorDecoder implements ErrorDecoder
{
    /**
     * Feign 异常解析
     *
     * @param methodKey 方法名
     * @param response  响应体
     *
     * @return MyException
     */
    @Override
    public Exception decode(String methodKey, Response response)
    {
        log.error("feign client error,response is {}:", response);

        String body = "";
        try
        {
            //获取数据
            body = Util.toString(response.body().asReader(Charset.defaultCharset()));

            if(response.status() != 503)
            {
                ResultInfo resultData = JSON.parseObject(body, ResultInfo.class);
                if (null != resultData && resultData.getCode().equals(EC.ERROR_SYSTEM_EXCEPTION.getCode()))
                {
                    return new MyException(resultData.getCode(), resultData.getMsg());
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return new MyException("Feign client 调用异常 " + body);
    }
}
