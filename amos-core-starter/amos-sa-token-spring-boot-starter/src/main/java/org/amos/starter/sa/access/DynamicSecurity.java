package org.amos.starter.sa.access;


/**
 * @desc: sa-token 动态权限相关 接口
 * @author: liubt
 * @date: 2022-08-17 09:24
 **/
public interface DynamicSecurity {
    /**
     * 执行顺序
     * @return
     */
    Integer order();

    /**
     * 路由检查操作
     * @param handler
     */
    Boolean decide(Object handler);
}
