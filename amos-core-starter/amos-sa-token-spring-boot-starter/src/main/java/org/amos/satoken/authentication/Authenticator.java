package org.amos.satoken.authentication;

import cn.hutool.core.lang.Dict;
import org.amos.satoken.domain.bo.AuthInfo;
import org.amos.satoken.handler.AuthHandler;

/**
 * @desc: 用户认证抽象类
 * @author: liubt
 * @date: 2022-08-17 15:05
 **/
public interface Authenticator extends AuthHandler {
    void preHandle() throws Exception;

    void afterCompletion() throws Exception;

    default AuthInfo postHandle(Dict param) throws Exception {
        this.preHandle();
        AuthInfo authInfo = this.authenticate(param);
        this.afterCompletion();
        return authInfo;
    }
}
