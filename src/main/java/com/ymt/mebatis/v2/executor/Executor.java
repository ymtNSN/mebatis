package com.ymt.mebatis.v2.executor;

/**
 * @Description TODO
 * @Author yangmingtian
 * @Date 2019/5/15
 */
public interface Executor {
    <T> T query(String statement, Object[] parameter, Class pojo);
}
