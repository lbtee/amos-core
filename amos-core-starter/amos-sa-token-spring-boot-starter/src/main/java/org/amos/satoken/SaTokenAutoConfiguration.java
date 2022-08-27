package org.amos.satoken;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amos.satoken.access.AccessTokenHandler;
import org.amos.satoken.access.DynamicSecurityManager;
import org.amos.satoken.properties.HttpUrlFilterProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

/**
 * Sa-Token 代码方式进行配置 
 * @author kong 
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(HttpUrlFilterProperties.class)
@RequiredArgsConstructor
public class SaTokenAutoConfiguration implements WebMvcConfigurer {

	private final AccessTokenHandler accessTokenHandler;
	private final DynamicSecurityManager dynamicSecurityManager;
	private final HttpUrlFilterProperties httpUrlFilterProperties;
	/**
	 * 注册 Sa-Token 的拦截器，打开注解式鉴权功能 
	 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
		// 注册路由拦截器，自定义验证规则
		registry.addInterceptor(new SaRouteInterceptor((req, res, handler)->
				SaRouter.match(Collections.singletonList("/**"))
						// 登录前放行接口
						.notMatch(httpUrlFilterProperties.getExcludePaths())
						// 登录检查
						.check(accessTokenHandler.handle())
						// 登录后放行接口、不做权限校验
						.notMatch(httpUrlFilterProperties.getIncludePaths())
						// 注册自定义鉴权路由配置
						.check(dynamicSecurityManager.excute(handler))
		)).addPathPatterns("/**");
		// 注册注解拦截器
		registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");
    }
}
