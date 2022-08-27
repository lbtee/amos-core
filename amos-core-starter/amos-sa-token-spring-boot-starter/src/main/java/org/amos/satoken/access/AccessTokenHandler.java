package org.amos.satoken.access;

import cn.dev33.satoken.fun.SaFunction;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Component;

/**
 * @desc: 登录检查
 * @author: liubt
 * @date: 2022-08-19 12:13
 **/
@Component
public class AccessTokenHandler {
    public SaFunction handle(){
        return ()->{
            StpUtil.checkLogin();
        };
    }
}
