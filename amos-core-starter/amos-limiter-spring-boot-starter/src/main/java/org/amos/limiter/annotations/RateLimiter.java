package org.amos.limiter.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 限流功能的注解
 * @author admin
 */
@Retention(RUNTIME)
@Target(value = {ElementType.METHOD})
@Documented
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
}
