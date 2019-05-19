package com.ymt.mebatis.v2.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description: 包装类，可被代理对象进行包装
 * @Author: yangmingtian
 * @Date: 2019/5/19
 */
public class Invocation {
    private Object object;
    private Method method;
    private Object[] args;

    public Invocation(Object object, Method method, Object[] args) {
        this.object = object;
        this.method = method;
        this.args = args;
    }

    public Object proceed() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(object, args);
    }

    public Object getObject() {
        return object;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }
}
