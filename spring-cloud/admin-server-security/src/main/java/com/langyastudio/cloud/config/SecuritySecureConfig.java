package com.langyastudio.cloud.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter
{
    private final String adminContextPath;

    public SecuritySecureConfig(AdminServerProperties adminServerProperties)
    {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        SavedRequestAwareAuthenticationSuccessHandler successHandler =
                new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminContextPath + "/");

        http.authorizeRequests()
                //1.授予对所有静态资产和登录页面的公共访问权限
                .antMatchers(adminContextPath + "/assets/**").permitAll()
                .antMatchers(adminContextPath + "/actuator/info").permitAll()
                .antMatchers(adminContextPath + "/actuator/health").permitAll()
                .antMatchers(adminContextPath + "/login").permitAll()

                //2.其他请求必须经过身份验证
                .anyRequest().authenticated()
                .and()

                //2.登录
                .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler)
                .and()

                //3.登出
                .logout().logoutUrl(adminContextPath + "/logout")
                .and()

                //4.开启http basic支持
                .httpBasic()
                .and()

                //5.开启基于cookie的csrf保护
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

                //6.忽略这些路径的csrf保护以便admin-client注册
                .ignoringRequestMatchers(
                        new AntPathRequestMatcher(adminContextPath + "/instances",
                                                  HttpMethod.POST.toString()),
                        new AntPathRequestMatcher(adminContextPath + "/instances/*",
                                                  HttpMethod.DELETE.toString()),
                        new AntPathRequestMatcher(adminContextPath + "/actuator/**")
                )
                .and()

                .rememberMe(rememberMe -> rememberMe.key(UUID.randomUUID().toString()).tokenValiditySeconds(1209600));
    }
}
