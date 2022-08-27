package org.amos.satoken.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @desc: 用户状态枚举
 * @author: liubt
 * @date: 2022-08-18 16:48
 **/
@Getter
@AllArgsConstructor
public enum UserStateEnums {
    NOT_INIT(0,"未初始化"),
    NORMAL(1,"正常状态"),
    DISABLE(2,"禁用状态");

    private Integer code;
    private String desc;
}
