package com.langyastudio.cloud.controller;

import com.langyastudio.cloud.domain.Oauth2TokenDto;
import com.langyastudio.common.constant.AuthConstant;
import com.langyastudio.common.data.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * 自定义 Oauth2 获取令牌接口
 */
@RestController
@RequestMapping("/oauth")
public class AuthController
{
    @Autowired
    private TokenEndpoint tokenEndpoint;

    /**
     * Oauth2 登录认证
     *
     * name = "grant_type", value = "授权模式", required = true
     * name = "client_id", value = "Oauth2客户端ID", required = true
     * name = "client_secret", value = "Oauth2客户端秘钥", required = true
     * name = "username", value = "登录用户名"
     * name = "password", value = "登录密码"
     * name = "refresh_token", value = "刷新token"
     */
    @PostMapping(value = "/token")
    public ResultInfo postAccessToken(Principal principal,
                                      @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException
    {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();

        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead(AuthConstant.JWT_TOKEN_PREFIX).build();

        return ResultInfo.data(oauth2TokenDto);
    }
}
