package org.amos.satoken.access;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.amos.core.basic.utils.HttpUtils;
import org.amos.satoken.api.UserRoleApi;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc: 动态权限匹配处理器
 * @author: liubt
 * @date: 2022-08-17 10:17
 **/
@Component
@RequiredArgsConstructor
public class DynamicAccessDecisionHandler implements DynamicSecurity{
    private final UserRoleApi userRoleApi;
    @Override
    public Integer order() {
        return 0;
    }

    @Override
    public Boolean decide(Object handler) {
        AntPathMatcher matcher = new AntPathMatcher();
        String uri = HttpUtils.getURI();
        SaSession tokenSession = StpUtil.getTokenSession();
        Object user = tokenSession.get("user");
//        List<String> paths = userRoleApi.getRolePathByRoleId("");
        List<String> paths = new ArrayList<>();
        paths.add("/system/dict/cache-list");
        return paths.stream().anyMatch(pattern->matcher.match(pattern, uri));
    }
}
