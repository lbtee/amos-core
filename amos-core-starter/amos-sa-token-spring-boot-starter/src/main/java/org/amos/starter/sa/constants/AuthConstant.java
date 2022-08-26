package org.amos.starter.sa.constants;


/**
 * @desc: 授权认证相关常量
 * @author: liubt
 * @date: 2022-08-15 13:21
 **/
public interface AuthConstant {
    /**
     * token
     */
    Integer TOKEN_EXPIRE = 24*60*60;
    /**
     * 授权类型
     */
    String AUTH_TYPE = "authType";
    /**
     * 用户名称
     */
    String USER_NAME = "username";
    /**
     * 密码
     */
    String PASSWORD = "password";
    /**
     * 验证码
     */
    String AUTH_CODE = "authcode";

}
