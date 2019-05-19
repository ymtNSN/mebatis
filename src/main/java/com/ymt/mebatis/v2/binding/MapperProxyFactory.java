package com.ymt.mebatis.v2.binding;

import com.ymt.mebatis.v2.session.DefaultSqlSession;

import java.lang.reflect.Proxy;

/**
 * @author ymtNSN
 *  用于产生MapperProxy代理类
 */
public class MapperProxyFactory<T> {

    private Class<T> mapperInterface;
    private Class object;

    public MapperProxyFactory(Class<T> mapperInterface, Class object) {
        this.mapperInterface = mapperInterface;
        this.object = object;
    }

    public T newInstance(DefaultSqlSession sqlSession){
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(),new Class[]{mapperInterface},new MapperProxy(sqlSession,object));
    }
}
