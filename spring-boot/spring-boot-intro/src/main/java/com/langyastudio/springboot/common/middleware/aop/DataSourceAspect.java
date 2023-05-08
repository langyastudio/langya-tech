package com.langyastudio.springboot.common.middleware.aop;

import com.alibaba.druid.pool.DruidDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * 监听 DataSource.getConnection，实现 JConnection 的返回
 *
 */
//@Component
//@Aspect
public class DataSourceAspect {

    @Pointcut("execution(* javax.sql.DataSource.getConnection(..))")
    public void pointCut() {}

    @Around("pointCut()")
    public Object handle(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();

        /**
         * todo 写死了DruidDataSource，之后完善
         */
        Object owner = ((MethodInvocationProceedingJoinPoint) proceedingJoinPoint).getThis();
        if (result != null) {
            DruidDataSource druidDataSource = null;
            if (owner instanceof DruidDataSource) {
                druidDataSource = ((DruidDataSource) owner);
            }

            if (null != druidDataSource) {
                String dbType = druidDataSource.getDbType();
            }
        }

        return result;
    }
}
