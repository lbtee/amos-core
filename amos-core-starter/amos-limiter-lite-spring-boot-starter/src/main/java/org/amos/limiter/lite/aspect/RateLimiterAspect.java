package org.amos.limiter.lite.aspect;

import lombok.extern.slf4j.Slf4j;
import org.amos.core.basic.exception.LimiterException;
import org.amos.limiter.lite.annotation.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * 限流器切面类
 * @author admin
 */
@Slf4j
@Aspect
@Component
public class RateLimiterAspect {
    private static final ConcurrentMap<String, com.google.common.util.concurrent.RateLimiter> RATE_LIMITER_CACHE = new ConcurrentHashMap<>();

    @Pointcut("@annotation(org.amos.limiter.lite.annotation.RateLimiter)")
    public void cut() {

    }

    @Around("cut()")
    public Object pointcut(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RateLimiter annotation = method.getAnnotation(RateLimiter.class);
        if (annotation != null) {
            double rate = annotation.rate();
            if (RATE_LIMITER_CACHE.get(method.getName()) == null) {
                RATE_LIMITER_CACHE.put(method.getName(), com.google.common.util.concurrent.RateLimiter.create(rate));
            }

            log.debug("【{}】的QPS设置为: {}", method.getName(), RATE_LIMITER_CACHE.get(method.getName()).getRate());
            // 尝试获取令牌
            if (RATE_LIMITER_CACHE.get(method.getName()) != null && !RATE_LIMITER_CACHE.get(method.getName()).tryAcquire(annotation.timeout(), annotation.timeUnit())) {
                throw new LimiterException("手速太快了，慢点儿吧~");
            }
        }
        return pjp.proceed();
    }
}
