package com.ymt.mebatis.v2.annotation;

import java.lang.annotation.*;

/**
 * 用于注解拦截器，指定拦截的方法
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Intercepts {
    String value();
}
