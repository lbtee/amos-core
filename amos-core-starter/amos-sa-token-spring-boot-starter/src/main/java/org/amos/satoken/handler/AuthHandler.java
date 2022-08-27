package org.amos.satoken.handler;

import cn.hutool.core.lang.Dict;
import org.amos.satoken.domain.bo.AuthInfo;

/**
 * 认证 授权 抽象接口
 */
public interface AuthHandler {
    /**
     * 登录
     * @param param
     * @return
     */
    AuthInfo authenticate(Dict param);

    /**
     * 认证类型
     * @return
     */
    Integer authType();
}
