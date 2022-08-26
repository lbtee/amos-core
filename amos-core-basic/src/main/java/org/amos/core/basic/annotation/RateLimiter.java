package org.amos.core.basic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author admin
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {
    /**
     * 限流接口名称
     * @return
     */
    String name();

    /**
     * 最大令牌数
     * @return
     */
    long permits() default 80;

    /**
     * 生成令牌速率（秒）
     * @return
     */
    long rate() default 100;

    /**
     * 拦截提示
     * @return
     */
    String blockMsg() default "limiter block";
}
