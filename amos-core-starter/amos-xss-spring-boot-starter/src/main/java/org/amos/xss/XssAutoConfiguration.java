package org.amos.xss;

import lombok.extern.slf4j.Slf4j;
import org.amos.xss.cleaner.DefaultXssCleaner;
import org.amos.xss.cleaner.XssCleaner;
import org.amos.xss.config.XssProperties;
import org.amos.xss.core.XssFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author Hccake 2021/3/8
 * @version 1.0
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(XssProperties.class)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = XssProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class XssAutoConfiguration {

	/**
	 * Xss 清理器配置
	 * @return XssCleaner
	 */
	@ConditionalOnMissingBean(XssCleaner.class)
	@Bean
	public XssCleaner xssCleaner() {
		return new DefaultXssCleaner();
	}

	/**
	 * 主要用于过滤 QueryString, Header 以及 form 中的参数
	 * @param xssProperties 安全配置类
	 * @return FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean<XssFilter> xssFilterRegistrationBean(XssProperties xssProperties,
																	   XssCleaner xssCleaner) {
		log.info("=========================XSS Filter Has Open=========================");
		XssFilter xssFilter = new XssFilter(xssProperties, xssCleaner);
		FilterRegistrationBean<XssFilter> registrationBean = new FilterRegistrationBean<>(xssFilter);
		registrationBean.setOrder(-1);
		return registrationBean;
	}
}
