package com.ymt.mebatis.v2.executor;

/**
 * @Description: TODO
 * @Author: yangmingtian
 * @Date: 2019/5/19
 */
public class SimpleExecutor implements Executor {
    @Override
    public <T> T query(String statement, Object[] parameter, Class pojo) {
        StatementHandler statementHandler = new StatementHandler();
        return statementHandler.query(statement, parameter, pojo);
    }
}
