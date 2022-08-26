package org.amos.starter.limiter.aspect;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amos.core.basic.exception.LimiterException;
import org.amos.starter.limiter.annotations.RateLimiter;
import org.amos.starter.limiter.components.AbstractRateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 限流器切面类
 * @author admin
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {
    private final AbstractRateLimiter rateLimiter;
    @Pointcut("@annotation(org.amos.starter.limiter.annotations.RateLimiter)")
    public void cut(){};

    @Before("cut()")
    public void before(JoinPoint jp) {
        Method method = ((MethodSignature)jp.getSignature()).getMethod();
        RateLimiter annotation = method.getAnnotation(RateLimiter.class);
        if (annotation != null) {
            String name = annotation.name();
            long permits = annotation.permits();
            long rate = annotation.rate();
            // 执行限流判断
            boolean ret = this.rateLimiter.tryAcquire(name, permits, rate);
            if (!ret) {
                log.error("the interface has been access limit by RateLimiter, rate:{},permits:{}",rate,permits);
                throw new LimiterException("手速太快了，慢点儿吧~");
            }
        }
    }
}
