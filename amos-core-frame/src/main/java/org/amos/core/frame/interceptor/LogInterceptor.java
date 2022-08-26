package org.amos.core.frame.interceptor;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @desc: log mdc 拦截器
 * @author: liubt
 * @date: 2022-08-26 09:42
 **/
public class LogInterceptor implements HandlerInterceptor {

    private static final String TRACE_ID = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader(TRACE_ID);
        if (StrUtil.isBlank(traceId)) {
            traceId = UUID.fastUUID().toString();
        }
        // 添加响应头
        response.setHeader(TRACE_ID, traceId);
        MDC.put(TRACE_ID, traceId);
        return Boolean.TRUE;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}