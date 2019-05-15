package com.ymt.mebatis.v1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description TODO
 * @Author yangmingtian
 * @Date 2019/5/15
 */
public class MyMapperProxy implements InvocationHandler {

    private MySqlSession sqlSession;

    public MyMapperProxy(MySqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String mapperInterface = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String statementId = mapperInterface+"."+methodName;
        return sqlSession.selectOne(statementId,args[0]);
    }
}
