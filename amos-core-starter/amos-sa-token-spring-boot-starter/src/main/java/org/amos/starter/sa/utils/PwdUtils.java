package org.amos.starter.sa.utils;

import cn.hutool.crypto.digest.BCrypt;

/**
 * @desc: 密码工具类
 * @author: liubt
 * @date: 2022-08-19 09:18
 **/
public class PwdUtils {
    /**
     * 加密密码
     * @param pwd
     * @return
     */
    public static String encode(String pwd) {
        return BCrypt.hashpw(pwd, BCrypt.gensalt());
    }

    /**
     * 密码校验
     * @param pwd
     * @param hashedPwd
     * @return
     */
    public static boolean match(String pwd, String hashedPwd) {
        return BCrypt.checkpw(pwd, hashedPwd);
    }
}
