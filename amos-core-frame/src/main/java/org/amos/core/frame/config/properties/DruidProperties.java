package org.amos.core.frame.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidProperties {

    /**
     * url
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 驱动名称
     */
    private String driverClassName;
    /**
     *
     */
    private int initialSize;
    /**
     *
     */
    private int maxActive;
    /**
     *
     */
    private int minIdle;
    /**
     *
     */
    private int maxWait;
    /**
     *
     */
    private boolean poolPreparedStatements;
    /**
     *
     */
    private int maxPoolPreparedStatementPerConnectionSize;
    /**
     *
     */
    private int timeBetweenEvictionRunsMillis;
    /**
     *
     */
    private int minEvictableIdleTimeMillis;
    /**
     *
     */
    private int maxEvictableIdleTimeMillis;
    /**
     *
     */
    private String validationQuery;
    /**
     *
     */
    private boolean testWhileIdle;
    /**
     *
     */
    private boolean testOnBorrow;
    /**
     *
     */
    private boolean testOnReturn;
    /**
     *
     */
    private String filters;
    /**
     *
     */
    private String connectionProperties;
}
