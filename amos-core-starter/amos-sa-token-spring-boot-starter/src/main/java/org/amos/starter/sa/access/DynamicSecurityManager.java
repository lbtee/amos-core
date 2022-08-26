package org.amos.starter.sa.access;

import cn.dev33.satoken.fun.SaFunction;
import cn.dev33.satoken.router.SaRouter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amos.core.basic.utils.HttpUtils;
import org.amos.starter.sa.exception.DynamicAccessException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * @desc: sa 路由规则匹配处理器
 * @author: liubt
 * @date: 2022-08-17 09:33
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicSecurityManager implements InitializingBean {
    private final List<DynamicSecurity> dynamicSecurities;
    @Override
    public void afterPropertiesSet() throws Exception {
        dynamicSecurities.stream().sorted(Comparator.comparing(DynamicSecurity::order));
    }

    /**
     * 路由处理入口
     * @param handler
     * @return
     */
    public SaFunction excute(Object handler){
        return ()->{
            boolean check = dynamicSecurities.stream().anyMatch(dynamicSecurity -> dynamicSecurity.decide(handler));
            if (check) {
                SaRouter.stop();
            } else {
                log.warn("{} 没有对应的权限", HttpUtils.getURI());
                throw new DynamicAccessException(HttpUtils.getURI() + " 没有对应的权限");
            }
        };
    }
}
