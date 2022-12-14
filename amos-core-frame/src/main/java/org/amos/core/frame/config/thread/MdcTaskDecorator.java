package org.amos.core.frame.config.thread;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

/**
 * @desc: mdc 任务装饰器
 * @author: liubt
 * @date: 2022-08-26 10:49
 **/
public class MdcTaskDecorator implements TaskDecorator{
    /**
     * 使异步线程池获得主线程的上下文
     * @param runnable
     * @return
     */
    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String,String> map = MDC.getCopyOfContextMap();
        return () -> {
            try{
                MDC.setContextMap(map);
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}