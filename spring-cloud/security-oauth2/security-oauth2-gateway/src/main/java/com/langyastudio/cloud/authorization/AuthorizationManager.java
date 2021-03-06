package com.langyastudio.cloud.authorization;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONUtil;
import com.langyastudio.cloud.config.IgnoreUrlsConfig;
import com.langyastudio.common.constant.AuthConstant;
import com.langyastudio.common.domain.UserDto;
import com.nimbusds.jose.JWSObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.text.ParseException;
import java.util.List;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限（基于路径匹配器授权）
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext>
{
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext)
    {
        ServerHttpRequest request     = authorizationContext.getExchange().getRequest();
        URI               uri         = request.getURI();
        PathMatcher       pathMatcher = new AntPathMatcher();

        //白名单路径直接放行
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls)
        {
            if (pathMatcher.match(ignoreUrl, uri.getPath()))
            {
                return Mono.just(new AuthorizationDecision(true));
            }
        }

        //对应跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS)
        {
            return Mono.just(new AuthorizationDecision(true));
        }

        //不同用户体系登录不允许互相访问
        try
        {
            String token = request.getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
            if (CharSequenceUtil.isEmpty(token))
            {
                return Mono.just(new AuthorizationDecision(false));
            }

            String    realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String    userStr   = jwsObject.getPayload().toString();
            UserDto   userDto   = JSONUtil.toBean(userStr, UserDto.class);
            if (AuthConstant.ADMIN_CLIENT_ID.equals(userDto.getClientId()) && !pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath()))
            {
                return Mono.just(new AuthorizationDecision(false));
            }
            if (AuthConstant.PORTAL_CLIENT_ID.equals(userDto.getClientId()) && pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath()))
            {
                return Mono.just(new AuthorizationDecision(false));
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return Mono.just(new AuthorizationDecision(false));
        }

        //非管理端路径直接放行
        if (!pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath()))
        {
            return Mono.just(new AuthorizationDecision(true));
        }

        //管理端路径需校验权限
        //从Redis中获取当前路径可访问角色列表
        Object       obj         = redisTemplate.opsForHash().get(AuthConstant.RESOURCE_ROLES_MAP_KEY, uri.getPath());
        List<String> authorities = Convert.toList(String.class, obj);
        //authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());

        //认证通过且角色匹配的用户可访问当前路径
        List<String> finalAuthorities = authorities;
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                //roleId是请求用户的角色(格式:ROLE_{roleId})，authorities是请求资源所需要角色的集合
                .any(authority -> {
                    String roleCode = authority.substring(AuthConstant.AUTHORITY_PREFIX.length()); // 用户的角色
                    // 如果是超级管理员则放行

                    return CollUtil.isNotEmpty(finalAuthorities) && finalAuthorities.contains(roleCode);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}
