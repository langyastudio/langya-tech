package com.langyastudio.springboot.bean.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * user 传入参数
 */
@Data
public class UserParam
{
    /**
     * 长度2-20
     * 不能为null
     */
    @NotNull
    @Size(min=2, max = 20)
    private String userName;

    /**
     * 长度2-20
     */
    @Size(min=2, max = 20)
    private String nickName;

    /**
     * 长度2-20
     * 邮箱格式
     */
    @NotNull
    @Email(message = "邮箱格式有误")
    private String email;

    /**
     * 手机号正则匹配
     */
    @Pattern(regexp = "^1[3,4,5,6,7,8,9]{1}[0-9]{9}$")
    private String telephone;
}