package org.amos.satoken.api;

import org.amos.satoken.domain.bo.AuthInfo;

/**
 * @desc: 用户认证接口
 * @author: liubt
 * @date: 2022-08-17 10:39
 **/
public interface UserAuthApi {
    /**
     * 根据用户名密码获取用户
     * @param account
     * @return
     */
    AuthInfo getUserByAccount(String account);

    /**
     * 根据账户名和手机验证码称获取用户信息
     * @param account
     * @param authCode
     * @return
     */
    AuthInfo getUserByAccountAndAuthCode(String account, String authCode);

    /**
     * 根据社交软件用户id和系统类别获取用户信息
     * @param socialId
     * @param sourceType
     * @return
     */
    AuthInfo authBySocialIdAndSourceType(String socialId, Integer sourceType);
}
