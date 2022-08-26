package org.amos.core.frame.config.properties;

import lombok.Data;

/**
 * @desc: 线程池通用参数抽象
 * @author: liubt
 * @date: 2022-08-26 11:06
 **/
@Data
public abstract class AbstractThreadPoolProperties {
    /**
     * 核心线程数,默认：Java虚拟机可用线程数
     */
    private Integer corePoolSize;
    /**
     * 线程池最大线程数,默认：40000
     */
    private Integer maxPoolSize;
    /**
     * 线程队列最大线程数,默认：80000
     */
    private Integer queueCapacity;
    /**
     * 自定义线程名前缀，默认：default-thread-pool-
     */
    private String threadNamePrefix = "default-thread-pool-";
    /**
     * 线程池中线程最大空闲时间，默认：60，单位：秒
     */
    private Integer keepAliveSeconds = 60;
}
