package com.ymt.mebatis.v2.plugin;

import com.ymt.mebatis.v2.annotation.Intercepts;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description: 代理类，用于代理被拦截对象  同时提供了创建代理类的方法
 * @Author: yangmingtian
 * @Date: 2019/5/19
 */
public class Plugin implements InvocationHandler {
    private Object target;
    private Interceptor interceptor;

    public Plugin(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object wrap(Object obj, Interceptor interceptor) {
        Class<?> clazz = obj.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new Plugin(obj, interceptor));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (interceptor.getClass().isAnnotationPresent(Intercepts.class)) {
            if (method.getName().equals(interceptor.getClass().getAnnotation(Intercepts.class).value())) {
                return interceptor.intercept(new Invocation(target, method, args));
            }
        }
        return method.invoke(target, method, args);
    }
}
