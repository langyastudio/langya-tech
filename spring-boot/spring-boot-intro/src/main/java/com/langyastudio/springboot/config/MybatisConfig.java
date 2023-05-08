package com.langyastudio.springboot.config;

import com.langyastudio.springboot.middleware.ReplaceTableInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.List;


/**
 * Mybatis Plus
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.langyastudio.springboot.mapper"})
public class MybatisConfig
{
    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @PostConstruct
    public void envInterceptor() {
        ReplaceTableInterceptor envInterceptor = new ReplaceTableInterceptor();
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(envInterceptor);
        }
    }
}