package org.amos.satoken.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.exception.AuthException;
import org.amos.satoken.api.UserAuthApi;
import org.amos.satoken.authentication.Authenticator;
import org.amos.satoken.domain.bo.AuthInfo;
import org.amos.satoken.enums.UserStateEnums;
import org.amos.satoken.utils.PwdUtils;
import org.amos.satoken.constants.AuthConstant;
import org.amos.satoken.constants.AuthTypeEnums;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
@Slf4j
@Component
@RequiredArgsConstructor
public class AccountAuthHandler implements Authenticator {

    public static final Integer AUTH_TYPE = AuthTypeEnums.ACCOUNT.getCode();
    private final UserAuthApi userAuthApi;
    @Override
    public AuthInfo authenticate(Dict param) {
        String username = param.getStr(AuthConstant.USER_NAME);
        String password = param.getStr(AuthConstant.PASSWORD);
        log.info("开始时间============》{}", DateUtil.formatTime(new Date()));
        // 执行认证
        AuthInfo authInfo = userAuthApi.getUserByAccount(username);
        log.info("结束时间============》{}", DateUtil.formatTime(new Date()));
        // 用户状态检查
        if (Objects.isNull(authInfo)){
            throw new AuthException("该账号不存在!");
        }
        if (!PwdUtils.match(password, authInfo.getPassword())) {
            throw new AuthException("该账号密码有误!");
        }
        if (UserStateEnums.NOT_INIT.getCode().equals(authInfo.getStatus())) {
            throw new AuthException("该账号未启用,请联系管理员!");
        }
        if (UserStateEnums.DISABLE.getCode().equals(authInfo.getStatus())) {
            throw new AuthException("该账号已封禁!");
        }
        return authInfo;
    }

    @Override
    public Integer authType() {
        return AUTH_TYPE;
    }

    @Override
    public void preHandle() throws Exception {

    }

    @Override
    public void afterCompletion() throws Exception {

    }
}
