package com.langyastudio.springboot.middleware;

import com.langyastudio.springboot.common.config.ErpDbConfig;
import com.langyastudio.springboot.utils.spring.SpringContextUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：
 * <p>
 * Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
 * ParameterHandler (getParameterObject, setParameters)
 * ResultSetHandler (handleResultSets, handleOutputParameters)
 * StatementHandler (prepare, parameterize, batch, update, query)
 *
 * @author jiangjiaxiong
 * @date 2023年05月05日 15:00
 */
@Intercepts({
        // method = "query"拦截select方法、而method = "update"则能拦截insert、update、delete的方法
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class})
})
public class ReplaceTableInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            ErpDbConfig dbConfig = SpringContextUtils.getBean(ErpDbConfig.class);
            if(null != dbConfig && dbConfig.getCompatibility()){

                List<String> replaceTables = dbConfig.getReplaceTables();
                if (null != replaceTables && replaceTables.size() > 0) {
                    Object[] argsArr = invocation.getArgs();

                    MappedStatement ms              = (MappedStatement) argsArr[0];
                    Object          parameterObject = argsArr[1];
                    BoundSql        boundSql        = ms.getBoundSql(parameterObject);

                    // sql命令类型
                    //SqlCommandType sqlCommandType = ms.getSqlCommandType();

                    //获取到拥有占位符的sql语句
                    String sql = boundSql.getSql();

                    System.out.println("拦截前sql :" + sql);
                    //判断是否需要替换表名
                    if (isReplaceTableName(replaceTables, sql)) {
                        String newSql = sql;

                        String schema = dbConfig.getSchema();
                        for (String item : replaceTables) {
                            newSql = sql.replace(item, schema + "." + item);
                        }
                        System.out.println("拦截后sql :" + newSql);

                        //重新生成一个BoundSql对象
                        BoundSql bs = new BoundSql(ms.getConfiguration(), newSql, boundSql.getParameterMappings(), parameterObject);

                        // start 解决There is no getter for property named '__frch_criterion_1' in 报错
                        for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
                            String property = parameterMapping.getProperty();
                            if (boundSql.hasAdditionalParameter(property)) {
                                bs.setAdditionalParameter(property, boundSql.getAdditionalParameter(property));
                            }
                        }
                        // end 解决There is no getter for property named '__frch_criterion_1' in 报错

                        //重新生成一个MappedStatement对象
                        MappedStatement newMs = copyMappedStatement(ms, new BoundSqlSqlSource(bs));

                        //赋回给实际执行方法所需的参数中
                        argsArr[0] = newMs;
                    }
                }
            }
        }catch (Throwable t) {
            System.out.println(t);
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /***
     * 判断是否需要替换表名
     * @param sql
     * @return
     */
    private boolean isReplaceTableName(List<String> replaceTables, String sql) {
        for (String tableName : replaceTables) {
            if (sql.contains(tableName)) {
                return true;
            }
        }
        return false;
    }

    /***
     * 复制一个新的MappedStatement
     * @param ms
     * @param newSqlSource
     * @return
     */
    private MappedStatement copyMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource
                , ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(String.join(",", ms.getKeyProperties()));
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    /***
     * MappedStatement构造器接受的是SqlSource
     * 实现SqlSource接口，将BoundSql封装进去
     */
    public static class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}

