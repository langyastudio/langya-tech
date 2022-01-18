package com.langyastudio.common.constant;

/**
 * 权限相关常量定义
 */
public interface AuthConstant
{
    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * 后台client_id
     */
    String ADMIN_CLIENT_ID = "api-admin";

    /**
     * 前端client_id
     */
    String PORTAL_CLIENT_ID = "api-portal";

    /**
     * 后台接口路径匹配
     */
    String ADMIN_URL_PATTERN = "/admin/**";

    /**
     * Redis缓存权限规则key
     */
    String RESOURCE_ROLES_MAP_KEY = "auth:resourceRolesMap";

    /**
     * 认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Hacfin ";

    /**
     * 用户信息Http请求头
     */
    String USER_TOKEN_HEADER = "user";

    /**
     * 黑名单
     */
    String TOKEN_BLACKLIST_PREFIX = "blacklist";
}
