package org.amos.core.frame.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @desc: 消息队列线程池配置
 * @author: liubt
 * @date: 2022-08-26 11:14
 **/
@Data
@Component
@ConfigurationProperties(prefix = "amos.task-pool.mq")
public class MQThreadPoolPropertiesProperties extends AbstractThreadPoolProperties {}

