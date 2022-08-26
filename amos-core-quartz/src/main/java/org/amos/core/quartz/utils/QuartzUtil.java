/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.amos.core.quartz.utils;

import lombok.extern.slf4j.Slf4j;
import org.amos.core.basic.exception.ServiceException;
import org.amos.core.quartz.entity.QuartzJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuartzUtil {

    private static final String TASK_NAME = "AMOS_TASK_";

    @Autowired
    private Scheduler scheduler;

    public void add(QuartzJob quartzJob){
        try {
            //构建job信息
            String jobClassName = quartzJob.getBeanName();
            String params = quartzJob.getParams();
            String cronExpression = quartzJob.getCronExpression();
            JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass())
                    .withIdentity(TASK_NAME + quartzJob.getId())
                    .usingJobData("parameter", params)
                    .build();

            //表达式调度构建器(即任务执行的时间) 使用withMisfireHandlingInstructionDoNothing() 忽略掉调度暂停过程中没有执行的调度
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName)
                    .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
                log.error(e.toString());
        } catch (Exception e){
            log.error("创建定时任务失败", e);
            throw new ServiceException("创建定时任务失败");
        }
    }

    /**
     * 暂停任务
     * @param quartzJob
     */
    public void pause(QuartzJob quartzJob){
        try {
            JobKey jobKey = JobKey.jobKey(TASK_NAME + quartzJob.getId());
            scheduler.pauseJob(jobKey);
        } catch (Exception e){
            log.error("定时任务暂停失败", e);
            throw new ServiceException("定时任务暂停失败");
        }
    }

    /**
     * 恢复任务
     * @param quartzJob
     */
    public void resume(QuartzJob quartzJob){
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(TASK_NAME + quartzJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if(trigger == null) {
                add(quartzJob);
            }
            JobKey jobKey = JobKey.jobKey(TASK_NAME + quartzJob.getId());
            scheduler.resumeJob(jobKey);
        } catch (Exception e){
            log.error("恢复定时任务失败", e);
            throw new ServiceException("恢复定时任务失败");
        }
    }

    /**
     * 删除任务
     * @param quartzJob /
     */
    public void delete(QuartzJob quartzJob){
        try {
            JobKey jobKey = JobKey.jobKey(TASK_NAME + quartzJob.getId());
            scheduler.pauseJob(jobKey);
            scheduler.deleteJob(jobKey);
        } catch (Exception e){
            log.error("删除定时任务失败", e);
            throw new ServiceException("删除定时任务失败");
        }
    }

    public static Job getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (Job) class1.newInstance();
    }
}
