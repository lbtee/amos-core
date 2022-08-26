package org.amos.starter.sa.handler;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Dict;
import lombok.SneakyThrows;
import me.zhyd.oauth.exception.AuthException;
import org.amos.core.basic.utils.HttpUtils;
import org.amos.starter.sa.authentication.Authenticator;
import org.amos.starter.sa.constants.AuthConstant;
import org.amos.starter.sa.domain.bo.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @desc: 系统授权认证管理器
 * @author: liubt
 * @date: 2022-08-15 11:13
 **/
@Component
public class AmosAuth implements AuthManager {

    @Autowired(required = false)
    private Set<Authenticator> authenticators;

    private Map<Integer, Authenticator> authHandlerMap = new HashMap<>();
    @Override
    @PostConstruct
    public void init() {
        if (CollUtil.isNotEmpty(authenticators)){
            authenticators.stream().collect(Collectors.toMap(Authenticator::authType, Function.identity())).forEach((k,v) -> authHandlerMap.putIfAbsent(k,v));
        }
    }

    @SneakyThrows
    @Override
    public AuthInfo login(Dict param) {
        String deviceName = HttpUtils.getDeviceName();
        Integer authType = param.getInt(AuthConstant.AUTH_TYPE);
        Assert.notNull(authType, "authType can not null");
        Authenticator authHandler = authHandlerMap.get(authType);
        if (Objects.isNull(authHandler)){
            return new AuthInfo();
        }
        AuthInfo authInfo = authHandler.postHandle(param);
        if (Objects.isNull(authInfo)){
            throw new AuthException("用户身份认证异常");
        }

        SaLoginModel model = new SaLoginModel();
        model.setDevice(deviceName);
        model.setTimeout(AuthConstant.TOKEN_EXPIRE);

        StpUtil.login(authInfo.getId(),model);
        authInfo.setToken(StpUtil.getTokenValue());
        authInfo.setExpire(StpUtil.getTokenTimeout());
        return authInfo;
    }

    @Override
    public Boolean isLogin(Dict param) {
        return null;
    }

    @Override
    public Boolean logout(Dict param) {
        return null;
    }
}
