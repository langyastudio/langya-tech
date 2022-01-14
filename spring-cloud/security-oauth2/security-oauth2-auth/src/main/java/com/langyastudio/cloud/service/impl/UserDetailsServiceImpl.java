package com.langyastudio.cloud.service.impl;

import com.langyastudio.cloud.domain.SecurityUserDetails;
import com.langyastudio.cloud.service.UmsAdminService;
import com.langyastudio.common.constant.AuthConstant;
import com.langyastudio.common.data.EC;
import com.langyastudio.common.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户管理业务类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private UmsAdminService    adminService;
    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        String  clientId = request.getParameter("client_id");
        UserDto userDto  = null;
        if (AuthConstant.ADMIN_CLIENT_ID.equals(clientId))
        {
            userDto = adminService.loadUserByUsername(username);
        }
        if (null == userDto)
        {
            throw new UsernameNotFoundException(EC.ERROR_USER_PASSWORD_INCORRECT.getMsg());
        }

        SecurityUserDetails securityUser = new SecurityUserDetails(userDto);
        if (!securityUser.isEnabled())
        {
            throw new DisabledException(EC.ERROR_USER_ENABLED.getMsg());
        }
        else if (!securityUser.isAccountNonLocked())
        {
            throw new LockedException(EC.ERROR_USER_LOCKED.getMsg());
        }
        else if (!securityUser.isAccountNonExpired())
        {
            throw new AccountExpiredException(EC.ERROR_USER_EXPIRE.getMsg());
        }
        else if (!securityUser.isCredentialsNonExpired())
        {
            throw new CredentialsExpiredException(EC.ERROR_USER_UNAUTHORIZED.getMsg());
        }
        return securityUser;
    }

}
