package org.amos.starter.sa.handler;

import cn.hutool.core.lang.Dict;
import org.amos.starter.sa.domain.bo.AuthInfo;

/**
 * @desc: 授权认证管理器
 * @author: liubt
 * @date: 2022-08-15 11:09
 **/
public interface AuthManager {
    /**
     * 初始化操作
     */
    void init();
    /**
     * 通用登录方法
     * @param param
     * @return
     */
    AuthInfo login(Dict param);

    /**
     * 判断登录状态
     * @param param
     * @return
     */
    Boolean isLogin(Dict param);

    /**
     * 退出登录通用方法
     * @param param
     * @return
     */
    Boolean logout(Dict param);
}
