package com.langyastudio.cloud.holder;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.langyastudio.common.constant.AuthConstant;
import com.langyastudio.common.domain.UserDto;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取登录用户信息
 */
@Component
public class LoginUserHolder
{
    public UserDto getCurrentUser(HttpServletRequest request)
    {
        String userStr = request.getHeader(AuthConstant.USER_TOKEN_HEADER);

        JSONObject userJsonObject = new JSONObject(userStr);

        UserDto userDTO = new UserDto();
        userDTO.setUserName(userJsonObject.getStr("user_name"));
        userDTO.setClientId(userJsonObject.getStr("client_id"));
        userDTO.setRoles(Convert.toList(String.class, userJsonObject.get(AuthConstant.AUTHORITY_CLAIM_NAME)));
        return userDTO;
    }
}
