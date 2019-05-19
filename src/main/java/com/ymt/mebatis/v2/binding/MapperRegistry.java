package com.ymt.mebatis.v2.binding;

import com.ymt.mebatis.v2.session.DefaultSqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 维护接口与工厂的关系，用于获取MapperProxy代理对象
 * 工厂类指定了POJO类型，用于处理结果返回
 * @Author: yangmingtian
 * @Date: 2019/5/15
 */
public class MapperRegistry {

    /**
     * 接口与工厂的映射关系
     */
    private final Map<Class<?>, MapperProxyFactory> knownMappers = new HashMap<>();

    /**
     * 描述: 在configuration中解析接口上的注解时，存入接口和工厂类的映射关系
     * 此处传入pojo类型，是为了最终处理结果集的时候将结果转化为POJO类型
     *
     * @param:
     * @return:
     * @auther: yangmingtian
     */
    public <T> void addMapper(Class<T> clazz, Class pojo) {
        knownMappers.put(clazz,new MapperProxyFactory(clazz,pojo));
    }

    public <T> T getMapper(Class<T> clazz, DefaultSqlSession sqlSession){
        MapperProxyFactory mapperProxyFactory = knownMappers.get(clazz);
        if(mapperProxyFactory == null){
            throw new RuntimeException("Type: "+clazz +" can not find");
        }
        return (T) mapperProxyFactory.newInstance(sqlSession);
    }
}
