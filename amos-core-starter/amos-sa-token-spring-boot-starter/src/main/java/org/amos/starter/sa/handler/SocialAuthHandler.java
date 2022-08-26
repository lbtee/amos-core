package org.amos.starter.sa.handler;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.enums.AuthResponseStatus;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.amos.core.basic.utils.HttpUtils;
import org.amos.starter.sa.api.UserAuthApi;
import org.amos.starter.sa.authentication.Authenticator;
import org.amos.starter.sa.constants.AuthTypeEnums;
import org.amos.starter.sa.domain.bo.AuthInfo;
import org.amos.starter.sa.properties.SocialProperties;
import org.amos.starter.sa.utils.SocialUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class SocialAuthHandler implements Authenticator {

    public static final Integer AUTH_TYPE = AuthTypeEnums.SOCIAL.getCode();
    private final SocialProperties socialProperties;
    private final UserAuthApi userAuthApi;

    @Override
    public AuthInfo authenticate(Dict param) {
        HttpServletRequest request = HttpUtils.getRequest();
        String source = request.getParameter("source");
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        Assert.state(StrUtil.isAllNotBlank(source,code,state), "social auth has lack params");
        // todo 统一认证
        AuthRequest authRequest = SocialUtil.getAuthRequest(source, socialProperties);
        AuthCallback authCallback = new AuthCallback();
        authCallback.setCode(code);
        authCallback.setState(state);
        AuthResponse authResponse = authRequest.login(authCallback);
        AuthUser authUser;
        if (authResponse.getCode() == AuthResponseStatus.SUCCESS.getCode()) {
            authUser = (AuthUser) authResponse.getData();
        } else {
            throw new AuthException("认证异常,请重试");
        }

        // 组装数据
//        UserOauth userOauth = Objects.requireNonNull(BeanUtil.copy(authUser, UserOauth.class));
//        userOauth.setSource(authUser.getSource());
//        userOauth.setTenantId(tenantId);
//        userOauth.setUuid(authUser.getUuid());
        AuthInfo authInfo = userAuthApi.authBySocialIdAndSourceType(authUser.getUuid(), Integer.valueOf(source));
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
