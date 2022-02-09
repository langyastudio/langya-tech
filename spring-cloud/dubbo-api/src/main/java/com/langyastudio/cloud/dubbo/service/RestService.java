package com.langyastudio.cloud.dubbo.service;

import com.langyastudio.cloud.dubbo.bean.User;

import java.util.Map;

/**
 * Rest Service.
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 */
public interface RestService
{
    String param(String param);

    String params(int a, String b);

    String headers(String header, String header2, Integer param);

    String pathVariables(String path1, String path2, String param);

    String form(String form);

    User requestBodyMap(Map<String, Object> data, String param);

    Map<String, Object> requestBodyUser(User user);
}
