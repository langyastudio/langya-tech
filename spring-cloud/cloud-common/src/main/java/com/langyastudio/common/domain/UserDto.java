package com.langyastudio.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long         id;
    private String       username;
    private String       password;
    private Integer      status;
    private String       clientId;
    private List<String> roles;
}
