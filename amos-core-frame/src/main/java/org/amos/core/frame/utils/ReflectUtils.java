package org.amos.core.frame.utils;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc: 反射相关工具类
 * @author: liubt
 * @date: 2022-08-11 14:09
 **/
public class ReflectUtils {
    /**
     * 获取方法参数并将参数转换成map
     * @param method
     * @param args
     * @return
     */
    public static Map<String, Object> getParamsMap(Method method, Object[] args) {
        Map<String, Object> paramsMap = new HashMap<>();
        if (args != null) {
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                if (!(ServletRequest.class.isAssignableFrom(parameters[i].getType())
                        || ServletResponse.class.isAssignableFrom(parameters[i].getType())) && args[i] != null) {
                    if (Map.class.isAssignableFrom(parameters[i].getType())) {
                        paramsMap.putAll((Map<String, Object>) args[i]);
                    } else if (HttpServletRequest.class.isAssignableFrom(parameters[i].getType())) {
                        paramsMap.putAll(((HttpServletRequest) args[i]).getParameterMap());
                    } else {
                        RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
                        String parameterName = parameters[i].getName();
                        if (requestParam != null && StrUtil.isNotBlank(requestParam.value())) {
                            parameterName = requestParam.value();
                        } else if (requestParam != null && StrUtil.isNotBlank(requestParam.name())) {
                            parameterName = requestParam.name();
                        }
                        paramsMap.put(parameterName, args[i]);
                    }
                }
            }
        }
        return paramsMap;
    }
}
