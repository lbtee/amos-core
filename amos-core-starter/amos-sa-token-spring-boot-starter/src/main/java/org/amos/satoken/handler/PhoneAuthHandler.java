package org.amos.satoken.handler;

import cn.hutool.core.lang.Dict;
import lombok.RequiredArgsConstructor;
import org.amos.satoken.api.UserAuthApi;
import org.amos.satoken.authentication.Authenticator;
import org.amos.satoken.constants.AuthConstant;
import org.amos.satoken.constants.AuthTypeEnums;
import org.amos.satoken.domain.bo.AuthInfo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneAuthHandler implements Authenticator {

    public static final Integer AUTH_TYPE = AuthTypeEnums.PHONE.getCode();
    private final UserAuthApi userAuthApi;
    @Override
    public AuthInfo authenticate(Dict param) {
        String username = param.getStr(AuthConstant.USER_NAME);
        String authcode = param.getStr(AuthConstant.AUTH_CODE);
        AuthInfo authInfo = userAuthApi.getUserByAccountAndAuthCode(username, authcode);
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
