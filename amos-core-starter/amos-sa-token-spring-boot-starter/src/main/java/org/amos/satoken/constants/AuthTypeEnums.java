package org.amos.satoken.constants;

import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @desc: 授权认证类型枚举
 * @author: liubt
 * @date: 2022-08-15 13:29
 **/
@Getter
@AllArgsConstructor
public enum AuthTypeEnums {
    ACCOUNT(0,  "用户名密码授权"),
    PHONE(1,  "短信验证码授权"),
    SOCIAL(2,"社交软件登录");

    final Integer code;
    final String desc;

    public static String getName(Integer code) {
        for (AuthTypeEnums type : AuthTypeEnums.values()) {
            if (type.getCode().equals(code)) {
                return type.desc;
            }
        }
        return DbColumnType.STRING.getType();
    }
}