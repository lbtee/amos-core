package org.amos.core.frame.config.thread;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amos.core.frame.config.properties.AbstractThreadPoolProperties;
import org.amos.core.frame.config.properties.DefaultThreadPoolPropertiesProperties;
import org.amos.core.frame.config.properties.MQThreadPoolPropertiesProperties;
import org.amos.core.frame.config.properties.ThreadPoolProperties;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @desc: 线程池 配置
 * @author: liubt
 * @date: 2022-08-08 13:21
 **/
@Slf4j
@EnableAsync
@Configuration
@RequiredArgsConstructor
public class ThreadPoolConfig implements AsyncConfigurer {

    private final ThreadPoolProperties threadPoolProperties;
    private final DefaultThreadPoolPropertiesProperties defaultThreadPoolProperties;
    private final MQThreadPoolPropertiesProperties mqThreadPoolProperties;

    /**
     * 默认线程池
     */
    @Bean(name = "defaultAsyncExecutor")
    public Executor defaultAsyncExecutor() {
        return initAsyncExecutor(defaultThreadPoolProperties, (r, executor) -> {
            log.info("队列已满.......");
        });
    }
    @Bean(name = "mqAsyncExecutor")
    public Executor mqAsyncExecutor() {
        return initAsyncExecutor(mqThreadPoolProperties, new ThreadPoolExecutor.CallerRunsPolicy());
    }
    public Executor initAsyncExecutor(AbstractThreadPoolProperties properties, RejectedExecutionHandler rejectedExecutionHandler) {
        //Java虚拟机可用的处理器数
        int processors = Runtime.getRuntime().availableProcessors();
        //定义线程池
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(Objects.nonNull(properties.getCorePoolSize()) ? properties.getCorePoolSize() : processors);
        //线程池最大线程数,默认：40000
        executor.setMaxPoolSize(Objects.nonNull(properties.getMaxPoolSize()) ? properties.getMaxPoolSize() : 40000);
        //线程队列最大线程数,默认：80000
        executor.setQueueCapacity(Objects.nonNull(properties.getMaxPoolSize()) ? properties.getMaxPoolSize() : 80000);
        //线程名称前缀
        executor.setThreadNamePrefix(StrUtil.isNotEmpty(properties.getThreadNamePrefix()) ? properties.getThreadNamePrefix() : "amos-sync-thread-pool-");
        //线程池中线程最大空闲时间，默认：60，单位：秒
        executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
        //核心线程是否允许超时，默认:false
        executor.setAllowCoreThreadTimeOut(threadPoolProperties.isAllowCoreThreadTimeOut());
        //IOC容器关闭时是否阻塞等待剩余的任务执行完成，默认:false（必须设置setAwaitTerminationSeconds）
        executor.setWaitForTasksToCompleteOnShutdown(threadPoolProperties.isWaitForTasksToCompleteOnShutdown());
        //阻塞IOC容器关闭的时间，默认：10秒（必须设置setWaitForTasksToCompleteOnShutdown）
        executor.setAwaitTerminationSeconds(threadPoolProperties.getAwaitTerminationSeconds());
        //拒绝策略，默认是AbortPolicy
        executor.setRejectedExecutionHandler(rejectedExecutionHandler);
        executor.setTaskDecorator(new MdcTaskDecorator());
        //初始化
        executor.initialize();

        return executor;
    }

    /**
     * 异步方法执行的过程中抛出的异常捕获记录
     *
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            String msg = StrUtil.EMPTY;
            if (ObjectUtil.isNotEmpty(objects) && objects.length > 0) {
                msg = StrUtil.join(msg, "参数是：");
                for (int i = 0; i < objects.length; i++) {
                    msg = StrUtil.join(msg, objects[i], CharUtil.CR);
                }
            }
            if (Objects.nonNull(throwable)) {
                msg = StrUtil.join(msg, throwable);
            }
            log.error(msg, method.getDeclaringClass());
        };
    }
}
