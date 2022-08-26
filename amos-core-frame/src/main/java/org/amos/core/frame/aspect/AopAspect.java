package org.amos.core.frame.aspect;

import cn.hutool.core.util.ReflectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * @desc: aop公共类
 * @author: liubt
 * @date: 2022-08-11 10:35
 **/
public abstract class AopAspect implements InitializingBean {
    @Autowired(required = false)
    private AopManager aopManager;
    /**
     * 被切面类
     */
    protected Class<?> clazz;

    /**
     * 被切面方法
     */
    protected Method method;

    /**
     * 返回类型
     */
    protected Class<?> returnType;

    /**
     * 切面参数初始化
     * @param pjp
     */
    protected void init(ProceedingJoinPoint pjp) {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        this.clazz = targetMethod.getDeclaringClass();
        this.method = ReflectUtil.getMethod(clazz, targetMethod.getName(), targetMethod.getParameterTypes());
        this.returnType = method.getReturnType();
    }

    /**
     * 调用自定义切面处理方法
     * @param pjp
     * @param clazz
     * @param method
     * @return
     * @throws Throwable
     */
    protected Object doCustom(ProceedingJoinPoint pjp, Class<?> clazz, Method method) throws Throwable {
        return aopManager == null ? pjp.proceed() : aopManager.doCustom(pjp, clazz, method);
    }
}
