package com.ymt.mebatis.v2.plugin;

/**
 * @Description: 拦截器接口，所有自定义拦截器必须实现此接口
 * @Author: yangmingtian
 * @Date: 2019/5/19
 */
public interface Interceptor {
    /**
     * 插件的核心逻辑实现
     */
    Object intercept(Invocation invocation) throws Throwable;

    /**
     * 可被拦截对象进行代理
     *
     * @param target
     * @return
     */
    Object plugin(Object target);
}
