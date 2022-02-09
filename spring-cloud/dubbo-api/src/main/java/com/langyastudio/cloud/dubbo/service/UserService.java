package com.langyastudio.cloud.dubbo.service;

import com.langyastudio.cloud.dubbo.bean.User;

import java.util.Collection;

/**
 * {@link User} Service.
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 */
public interface UserService
{
    boolean save(User user);

    boolean remove(Long userId);

    Collection<User> findAll();
}