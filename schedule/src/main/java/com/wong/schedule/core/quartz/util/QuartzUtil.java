/*
 * Copyleft
 */
package com.wong.schedule.core.quartz.util;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.wong.schedule.core.quartz.entity.JobEntity;

/**
 * 封装了少量构建 quartz 任务时需要的对象的工具类 
 * @author 黄小天
 * @date 2019-02-23 23:31
 * @version 1.0
 */
public class QuartzUtil {
    
    public static void schedule(Scheduler scheduler, JobEntity entity) {
        try {
            scheduler.scheduleJob(buildJobDetail(entity.getJobKey(), entity.getJobClazz(), 
                    entity.getData()), entity.getTrigger());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static CronTrigger buildCronTrigger(String jobName, String jobGroup, String cron) {
        return TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
    }
    
    public static SimpleTrigger buildSimpleTrigger(String jobName, String jobGroup, int fixedRate, 
            int count, long initialDelay) {
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule();
        if (fixedRate > 0) {
            builder.withIntervalInSeconds(fixedRate);
        }
        if (count > 0) {
            builder.withRepeatCount(count);
        } else {
            builder.repeatForever();
        }
        TriggerBuilder<SimpleTrigger> trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobGroup)
                .withSchedule(builder);
        if (initialDelay > 0) {
            trigger.startAt(new Date(System.currentTimeMillis() + initialDelay*1000));
        }
        return trigger.build();
    }
    
    @SuppressWarnings("unchecked")
    public static JobDetail buildJobDetail(JobKey jobKey, Class<?> clazz, JobDataMap data) {
        if (data == null) {
            data = new JobDataMap();
        }
        JobBuilder builder = JobBuilder.newJob((Class<? extends Job>) clazz).withIdentity(jobKey);
        builder.setJobData(data);
        return builder.build();
    }
    
    public static TriggerKey buildTriggerKey(JobKey jobKey) {
        return new TriggerKey(jobKey.getName(), jobKey.getGroup());
    }
}
