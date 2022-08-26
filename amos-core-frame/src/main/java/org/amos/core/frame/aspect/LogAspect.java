package org.amos.core.frame.aspect;

import org.amos.core.basic.utils.HttpUtils;
import org.amos.core.frame.tool.log.AbstractLogAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @desc: 接口日志切面
 * @author: liubt
 * @date: 2022-08-11 10:25
 **/
@Aspect
@Component
public class LogAspect extends AbstractLogAspect {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PatchMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void cut(){}

    @Around("cut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        super.init(pjp);
        String url = HttpUtils.getRequest().getRequestURI();
        return doAround(pjp, url, method.getName());
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        if (excludeUrls == null) {
            excludeUrls = new ArrayList<>();
        }
        excludeUrls.add("/error");
        excludeUrls.add("/swagger-resources");
    }
}
