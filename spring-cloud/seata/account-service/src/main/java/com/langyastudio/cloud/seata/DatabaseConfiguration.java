package com.langyastudio.cloud.seata;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 数据库配置
 */
@Configuration
public class DatabaseConfiguration
{
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource storageDataSource()
    {
        return new DruidDataSource();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource)
    {
        return new JdbcTemplate(dataSource);
    }
}
