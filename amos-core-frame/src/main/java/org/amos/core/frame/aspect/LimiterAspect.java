package org.amos.core.frame.aspect;//package org.amos.core.aspect;
//
//import com.google.common.collect.Maps;
//import lombok.RequiredArgsConstructor;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//import org.amos.core.annotation.Limiter;
//import org.amos.core.exception.ViewsLimiterException;
//import org.amos.core.tool.limit.manager.LimiterManager;
//
//import java.util.Map;
//
//@Aspect
//@Component
//@RequiredArgsConstructor
//public class LimiterAspect {
//
//    private Map<String, Limiter> limiterMap = Maps.newConcurrentMap();
//
//    private final LimiterManager rateLimiterManager;
//
//    @Pointcut("@annotation(org.amos.core.annotation.Limiter)")
//    public void addPoint() {
//
//    }
//
//    @Around("addPoint()")
//    public Object doInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
//        Limiter limiter = getLimiter(joinPoint);
//        if (limiter.blockStrategy()) {
//            rateLimiterManager.acquire(limiter);
//        } else {
//            boolean tryAcquire = rateLimiterManager.tryAcquire(limiter);
//            if (!tryAcquire) {
//                throw new ViewsLimiterException(limiter.blockMsg());
//            }
//        }
//
//        return joinPoint.proceed();
//    }
//
//    private Limiter getLimiter(ProceedingJoinPoint joinPoint) {
//        String key = joinPoint.getSignature().toShortString();
//        Limiter result = limiterMap.get(key);
//        if (result == null) {
//            MethodSignature sig = (MethodSignature) joinPoint.getSignature();
//            result = sig.getMethod().getAnnotation(Limiter.class);
//            limiterMap.put(key, result);
//        }
//        return result;
//    }
//
//}
