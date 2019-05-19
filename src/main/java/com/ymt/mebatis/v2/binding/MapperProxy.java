package com.ymt.mebatis.v2.binding;

import com.ymt.mebatis.v2.session.DefaultSqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description: TODO
 * @Author: yangmingtian
 * @Date: 2019/5/18
 */
public class MapperProxy implements InvocationHandler {

    private DefaultSqlSession sqlSession;
    private Class object;

    public MapperProxy(DefaultSqlSession sqlSession, Class object) {
        this.sqlSession = sqlSession;
        this.object = object;
    }

    /**
     * 所有Mapper接口的方法调用都会走到这里
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String mapperInterface = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String statementId = mapperInterface + "." + methodName;
        if (sqlSession.getConfiguration().hasStatement(statementId)) {
            return sqlSession.selectOne(statementId, args, object);
        }
        return method.invoke(proxy, args);
    }
}
