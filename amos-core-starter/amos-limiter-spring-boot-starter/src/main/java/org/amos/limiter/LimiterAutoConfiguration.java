package org.amos.limiter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * redis限流器的自动配置类
 * @author admin
 */
@AutoConfiguration
public class LimiterAutoConfiguration {

    /**
     * redis的lua脚本对象
     * @return
     */
    @Bean
    public DefaultRedisScript<Boolean> redisScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/limit.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }
}
