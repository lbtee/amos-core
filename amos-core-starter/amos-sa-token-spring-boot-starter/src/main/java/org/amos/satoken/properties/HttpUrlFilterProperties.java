package org.amos.satoken.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = HttpUrlFilterProperties.PREFIX)
public class HttpUrlFilterProperties {

    public static final String PREFIX = "amos.security.filter-url";

    /**
     * 白名单URL
     */
    List<String> excludePaths = new ArrayList<>();
    /**
     * token 验证通过才能访问URL
     */
    List<String> includePaths = new ArrayList<>();

    public HttpUrlFilterProperties() {
        /**
         * 设置默认白名单
         */
        this.excludePaths.add("/doc.html");
        this.excludePaths.add("/swagger-resources/**");
        this.excludePaths.add("/configuration/ui");
        this.excludePaths.add("/v2/api-docs");
        this.excludePaths.add("/configuration/security");
        this.excludePaths.add("/swagger-ui.html");
        this.excludePaths.add("/webjars/**");
        this.excludePaths.add("/favicon.ico");
        this.excludePaths.add("/druid/**");
        this.excludePaths.add("/auth/**");
        this.excludePaths.add("/");
        /**
         * 设置默认需要验证url
         */
        this.includePaths.add("/t1");
        this.includePaths.add("/api/**");
    }
}
