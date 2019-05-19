package com.ymt.mebatis.v2.plugin;


import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 拦截器链，存放所有的拦截器，和被代理对象进行循环代理
 * @Author: yangmingtian
 * @Date: 2019/5/15
 */
public class IntercepterChain {

    private final List<Interceptor> interceptors = new ArrayList<>();

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public Object pluginAll(Object target) {
        for (Interceptor interceptor : interceptors) {
            target = interceptor.plugin(target);
        }
        return target;
    }

    public boolean hasPlugin() {
        return interceptors.size() != 0;
    }
}
