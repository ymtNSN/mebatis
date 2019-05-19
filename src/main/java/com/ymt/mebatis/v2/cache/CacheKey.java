package com.ymt.mebatis.v2.cache;

/**
 * @Description: 缓存Key
 * @Author: yangmingtian
 * @Date: 2019/5/19
 */
public class CacheKey {
    private static final int DEFAULT_HASHCODE = 17; // 默认哈希值
    private static final int DEFAULT_MULTIPLIER = 37; // 倍数

    private int hashCode;
    private int count;
    private int multiplier;

    /**
     * 构造函数
     */
    public CacheKey() {
        this.hashCode = DEFAULT_HASHCODE;
        this.count = 0;
        this.multiplier = DEFAULT_MULTIPLIER;
    }

    public int getCode() {
        return hashCode;
    }

    public void update(Object object) {
        int baseHashCode = object == null ? 1 : object.hashCode();
        count++;
        baseHashCode *= count;
        hashCode = multiplier * hashCode + baseHashCode;
    }
}
