package com.langyastudio.cloud.exception;

import com.langyastudio.common.data.EC;
import com.langyastudio.common.data.ResultInfo;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局处理 Oauth2 抛出的异常
 */
@ControllerAdvice
public class Oauth2ExceptionHandler
{
    @ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public ResultInfo handleOauth2(OAuth2Exception e)
    {
        return ResultInfo.data(EC.ERROR, e.getMessage());
    }
}
