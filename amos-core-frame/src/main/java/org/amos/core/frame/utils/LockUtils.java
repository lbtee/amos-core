package org.amos.core.frame.utils;

import cn.hutool.core.lang.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @desc: redis 分布式锁工具类
 * @author: liubt
 * @date: 2022-08-25 17:20
 **/
@Component
@RequiredArgsConstructor
public class LockUtils {

    private static final RedisScript<String> SCRIPT_LOCK = new DefaultRedisScript<>("return redis.call('set',KEYS[1]," +
            "ARGV[1],'NX','PX',ARGV[2])", String.class);
    private static final RedisScript<String> SCRIPT_UNLOCK = new DefaultRedisScript<>("if redis.call('get',KEYS[1]) " +
            "== ARGV[1] then return tostring(redis.call('del', KEYS[1])==1) else return 'false' end", String.class);
    private static final String LOCK_SUCCESS = "OK";
    /**
     * 默认过期时间10S
     */
    private static final Long EXPIRE = 10L;
    /**
     * 默认超时时间3S
     */
    private static final Long TIMEOUT = 3L;

    private final StringRedisTemplate redisTemplate;

    /**
     * 默认过期、超时时间
     * @param key
     * @return
     */
    public boolean lock(String key){
        return this.lock(key, EXPIRE, TIMEOUT);
    }

    /**
     * 默认超时时间
     * @param key
     * @param expire
     * @return
     */
    public boolean lock(String key, long expire){
        return this.lock(key, expire, TIMEOUT);
    }

    /**
     * 基础lock方法
     * @param key
     * @param expire
     * @param timeout
     * @return
     */
    public boolean lock(String key, long expire, long timeout) {
        String uuid = UUID.fastUUID().toString();
        String lock = redisTemplate.execute(SCRIPT_LOCK,
                redisTemplate.getStringSerializer(),
                redisTemplate.getStringSerializer(),
                Collections.singletonList(key),
                uuid, String.valueOf(expire));
        LockStateHolder.set(uuid);
        return LOCK_SUCCESS.equals(lock);
    }

    /**
     * 释放锁资源
     * @param key
     * @return
     */
    public boolean unLock(String key) {
        String uuid = LockStateHolder.get();
        LockStateHolder.remove();
        String releaseResult = redisTemplate.execute(SCRIPT_UNLOCK,
                redisTemplate.getStringSerializer(),
                redisTemplate.getStringSerializer(),
                Collections.singletonList(key), uuid);
        return Boolean.parseBoolean(releaseResult);
    }

    /**
     * 线程持有锁信息
     */
    public static class LockStateHolder {

        private static final ThreadLocal<String> STATE = new ThreadLocal<>();

        public static String get(){
            return STATE.get();
        }

        public static void set(String state){
            STATE.set(state);
        }

        public static void remove(){
            STATE.remove();
        }
    }
}
