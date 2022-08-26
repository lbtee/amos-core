package org.amos.core.frame.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @desc: 切面自定义处理接口
 * @author: liubt
 * @date: 2022-08-11 11:05
 **/
public interface AopManager {
    /**
     * 自定义切面处理方法
     * @param pjp
     * @param clazz
     * @param method
     * @return
     * @throws Exception
     */
    Object doCustom(ProceedingJoinPoint pjp, Class<?> clazz, Method method) throws Exception;
}
