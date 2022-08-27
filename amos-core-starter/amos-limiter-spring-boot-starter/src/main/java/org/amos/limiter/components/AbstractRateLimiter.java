package org.amos.limiter.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于redis的限流器
 * @author admin
 */
public abstract class AbstractRateLimiter {

    /**
     * 全局限流器key
     */
    protected static  final String GLOBAL_RATE_LIMITER_KEY = "GLOBAL_RATE_LIMITER_KEY";

    private static final Logger logger = LoggerFactory.getLogger(AbstractRateLimiter.class);

    /**
     * 是否开启限流
     */
    private boolean limited = true;

    /**
     * 开启限流功能
     */
    public void open() {
        if (!this.limited) {
            this.limited = true;
        } else {
            logger.info("the limiter has started...");
        }
    }

    /**
     * 关闭限流功能
     */
    public void close() {
        if (this.limited) {
            this.limited = false;
        } else {
            logger.info("the limiter has stopped...");
        }
    }

    /**
     * 获取令牌(指定接口限流)
     * @param interfaceName 需要限流的接口名
     * @param maxPermits 最大令牌数
     * @param tokensPerSeconds 每秒生成的令牌数
     * @return boolean 是否通过限流(获取到令牌)
     */
    protected abstract boolean acquire(String interfaceName, long maxPermits, long tokensPerSeconds);

    /**
     * 获取令牌(指定接口)
     * @param interfaceName 需要限流的接口名
     * @return boolean 是否通过限流(获取到令牌)
     */
    public boolean tryAcquire(String interfaceName, long permits, long rate) {
        if (this.limited) {
            return this.acquire(interfaceName, permits, rate);
        } else {
            return true;
        }
    }
}
