package org.amos.starter.sa.properties;

import lombok.Data;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthDefaultSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = SocialProperties.PREFIX)
public class SocialProperties {
    public static final String PREFIX = "amos.social";
    private Boolean enabled = false;
    private String url;
    private Map<AuthDefaultSource, AuthConfig> oauth = new HashMap<>();
}

