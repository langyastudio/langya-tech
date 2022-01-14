package com.langyastudio.cloud.component;

import com.langyastudio.cloud.domain.SecurityUserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * JWT 内容增强器
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer
{
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication)
    {
        SecurityUserDetails securityUser = (SecurityUserDetails) authentication.getPrincipal();

        //把用户名设置到JWT中
        Map<String, Object> info = new HashMap<>();
        info.put("user_name", securityUser.getUsername());
        info.put("client_id", securityUser.getClientId());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
