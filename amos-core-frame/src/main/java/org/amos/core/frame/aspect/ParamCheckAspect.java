package org.amos.core.frame.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.amos.core.basic.annotation.ParamCheck;
import org.amos.core.basic.exception.ServiceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @desc: 参数不为空校验切面
 * @author: liubt
 * @date: 2022-08-23 17:09
 **/
@Slf4j
@Aspect
@Component
public class ParamCheckAspect {
    @Pointcut("@annotation(org.amos.core.basic.annotation.ParamCheck)")
    public void cut(){}

    @Around("cut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = ((MethodSignature) pjp.getSignature());
        //得到拦截的方法
        Method method = signature.getMethod();
        //获取方法参数名
        String[] paramNames = signature.getParameterNames();
        //获取参数值
        Object[] paramValues = pjp.getArgs();
        //获取方法 ParamCheck注解
        ParamCheck annotation = method.getAnnotation(ParamCheck.class);
        String[] value = annotation.value();
        List<String> list = Arrays.asList(value);
        for (int i = 0; i < paramValues.length; i++) {
            boolean containValue = CollUtil.isNotEmpty(list) && !list.contains(paramNames[i]);
            if (containValue){
                continue;
            }
            if (ObjectUtil.isEmpty(paramValues[i])) {
                throw new ServiceException(StrUtil.format("{}不能为空", paramNames[i]));
            }
        }
        return pjp.proceed();
    }
}
