package com.ymt.mebatis.v2.session;


import com.ymt.mebatis.v2.executor.Executor;

/**
 * @Description mybatis的API，提供给应用层
 * @Author yangmingtian
 * @Date 2019/5/15
 */
public class DefaultSqlSession {

    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = configuration.newExecutor();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public <T> T getMapper(Class<T> clazz) {
        return configuration.getMapper(clazz, this);
    }

    public <T> T selectOne(String statement, Object[] parameter, Class pojo) {
        String sql = configuration.getMapperStatement(statement);
        return executor.query(sql, parameter, pojo);
    }
}
