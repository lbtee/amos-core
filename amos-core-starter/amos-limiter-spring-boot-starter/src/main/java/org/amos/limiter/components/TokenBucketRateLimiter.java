package org.amos.limiter.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 基于redis+lua的令牌桶限流器
 * @author admin
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenBucketRateLimiter extends AbstractRateLimiter {

    private final DefaultRedisScript<Boolean> script;
    private final RedisTemplate<String, Object> redisTemplate;
    /**
     * 限流检测(单个接口)
     * @param name 需要限流的接口名
     * @param permits 最大令牌数
     * @param rate 生成的令牌速率
     * @return 是否通过限流 true: 通过
     */
    @Override
    protected boolean acquire(String name, long permits, long rate) {
        // 错误的参数将不起作用
        if (rate <= 0 || rate <= 0) {
            log.warn("maxPermits and tokensPerSeconds can not be less than zero...");
            return true;
        }

        // 参数结构: KEYS = [限流的key]   ARGV = [最大令牌数, 每秒生成的令牌数, 本次请求的毫秒数]
        // 参数1: 脚本, 参数2: 脚本中的KEYS数组, 参数3: 脚本中的ARGV数组
        Boolean result = this.redisTemplate.execute(this.script, Collections.singletonList(name), permits, rate, System.currentTimeMillis());
        return result!=null && result;
    }
}
