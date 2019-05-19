package com.ymt.mebatis.v2.executor;

import com.ymt.mebatis.v2.cache.CacheKey;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: yangmingtian
 * @Date: 2019/5/19
 */
public class CachingExecutor implements Executor {

    private Map<Integer, Object> cache = new HashMap<>();
    private Executor delegate;

    public CachingExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T> T query(String statement, Object[] parameter, Class pojo) {
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(statement);
        cacheKey.update(joinStr(parameter));
        if (cache.containsKey(cacheKey.getCode())) {
            System.out.println("【命中缓存】");
            return (T) cache.get(cacheKey.getCode());
        } else {
            Object obj = delegate.query(statement, parameter, pojo);
            cache.put(cacheKey.getCode(), obj);
            return (T) obj;
        }
    }

    private String joinStr(Object[] objs) {
        StringBuilder sb = new StringBuilder();
        if (objs != null && objs.length > 0) {
            for (Object object : objs) {
                sb.append(object.toString() + ",");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
