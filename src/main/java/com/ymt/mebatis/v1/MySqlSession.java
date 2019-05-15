package com.ymt.mebatis.v1;

/**
 * @Description TODO
 * @Author yangmingtian
 * @Date 2019/5/15
 */
public class MySqlSession {
    private MyConfiguration configuration;

    private MyExecutor executor;

    public MySqlSession(MyConfiguration configuration, MyExecutor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T selectOne(String statementId, Object paramater) {
        String sql = MyConfiguration.sqlMappings.getString(statementId);
        if (null != sql || "".equals(sql)) {
            return executor.query(sql, paramater);
        }
        return null;
    }

    public <T> T getMapper(Class clazz) {
        return configuration.getMapper(clazz, this);
    }
}
