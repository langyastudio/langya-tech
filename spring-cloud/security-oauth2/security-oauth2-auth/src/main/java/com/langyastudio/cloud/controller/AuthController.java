package com.langyastudio.cloud.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.langyastudio.cloud.domain.Oauth2TokenDto;
import com.langyastudio.common.constant.AuthConstant;
import com.langyastudio.common.data.EC;
import com.langyastudio.common.data.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 自定义 Oauth2 获取令牌接口
 */
@RestController
@RequestMapping("/oauth")
public class AuthController
{
    private RedisTemplate redisTemplate;

    @Autowired
    private TokenEndpoint tokenEndpoint;

    /**
     * Oauth2 登录认证
     * <p>
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

    /**
     * 注销登录时，缓存JWT至Redis，且缓存有效时间设置为JWT的有效期
     * 请求资源时判断是否存在缓存的黑名单中，存在则拒绝访问。
     */
    @PostMapping(value = "/logout")
    public ResultInfo logout(HttpServletRequest request)
    {
        String     payload    = request.getHeader(AuthConstant.AUTHORITY_CLAIM_NAME);
        JSONObject jsonObject = JSONUtil.parseObj(payload);

        // JWT唯一标识
        String jti = jsonObject.getStr("jti");
        // JWT过期时间戳(单位:秒)
        long   exp = jsonObject.getLong("exp");

        long currentTimeSeconds = System.currentTimeMillis() / 1000;
        if (exp < currentTimeSeconds)
        {
            // token已过期
            return ResultInfo.data(EC.ERROR_USER_UNAUTHORIZED);
        }

        redisTemplate.opsForValue().set(AuthConstant.TOKEN_BLACKLIST_PREFIX + jti, null, (exp - currentTimeSeconds),
                                        TimeUnit.SECONDS);
        return ResultInfo.data();
    }
}
