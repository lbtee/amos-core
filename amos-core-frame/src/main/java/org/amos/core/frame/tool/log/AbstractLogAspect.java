package org.amos.core.frame.tool.log;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.amos.core.frame.aspect.AopAspect;
import org.amos.core.frame.utils.ReflectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

/**
 * @desc: 日志切面抽象类
 * @author: liubt
 * @date: 2022-08-11 11:14
 **/
@Slf4j
public abstract class AbstractLogAspect extends AopAspect {
    /**
     * 是否记录切面日志
     */
    @Value("${amos.log.show-default-log:true}")
    protected boolean showDefaultLog;

    /**
     * 排除的路径
     */
    @Value("${amos.log.exclude-urls:[]}")
    protected List<String> excludeUrls;
    /**
     * 执行切面
     * @param pjp
     * @param url
     * @param desc
     * @return
     * @throws Throwable
     */
    protected Object doAround(ProceedingJoinPoint pjp, String url, String... desc) throws Throwable {
        // 如果请求路径被排除，则放行
        if (StrUtil.startWithAny(url, excludeUrls.toArray(new String[]{}))) {
            return pjp.proceed();
        }
        return showDefaultLog ? doPrint(pjp, clazz, method, desc, StrUtil.blankToDefault(url, clazz.getName()))
                : doCustom(pjp, clazz, method);
    }
    /**
     * 通用日志打印方法
     * @param pjp
     * @param clazz
     * @param method
     * @param desc
     * @param url
     * @return
     * @throws Throwable
     */
    protected Object doPrint(ProceedingJoinPoint pjp, Class<?> clazz, Method method, String[] desc, String url)
            throws Throwable {
        Object[] args = pjp.getArgs();
        if (ObjectUtil.isEmpty(desc)) {
            desc = new String[] { clazz.getName(), clazz.getName() };
        }
        String description = StrUtil.join(",",desc);
        MDC.put("traceId", UUID.randomUUID().toString());
        long beginTime = DateUtil.current();
        log.info(StrBuilder.create()
                    .append("\n=========================begin record=========================\n")
                    .append("request desc   =>  ").append(description).append("\n")
                    .append("request url    =>  ").append(url).append("\n")
                    .append("request method =>  ").append(clazz.getName()).append(".").append(method.getName()).append("\n")
                    .append("request params =>  ").append(JSON.toJSONString(ReflectUtils.getParamsMap(method, args))).append("\n")
                    .append("execute start  =>  ")
                    .toString());
        Object res = doCustom(pjp, clazz, method);
        log.info(StrBuilder.create()
                    .append("\nrequest result =>  ").append(JSON.toJSONString(res)).append("\n")
                    .append("execute time   =>  ").append(String.valueOf(DateUtil.current()-beginTime)).append("毫秒 \n")
                    .append("=========================end   record=========================\n")
                    .toString());
        return res;
    }

}
