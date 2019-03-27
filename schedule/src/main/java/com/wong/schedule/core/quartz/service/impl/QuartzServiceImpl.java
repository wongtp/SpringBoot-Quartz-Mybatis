/*
 * Copyleft
 */
package com.wong.schedule.core.quartz.service.impl;

import javax.annotation.PostConstruct;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.wong.schedule.core.quartz.entity.JobEntity;
import com.wong.schedule.core.quartz.service.QuartzService;
import com.wong.schedule.core.quartz.util.QuartzUtil;

/**
 * 
 * @author 黄小天
 * @date 2019-02-22 23:30
 * @version 1.0
 */
@Component
public class QuartzServiceImpl implements QuartzService {
    
    @Autowired
    private SchedulerFactoryBean schedulerFactory;
    
    private Scheduler scheduler;
    
    @PostConstruct
    private void init() {
        scheduler = schedulerFactory.getScheduler();
    }
    
    @Override
    public void add(JobEntity entity) {
        QuartzUtil.schedule(scheduler, entity);
    }
    
    @Override
    public JobEntity fetch(String jobName, String jobGroup) {
        return fetch(JobKey.jobKey(jobName, jobGroup));
    }
    
    @Override
    public JobEntity fetch(JobKey jobKey) {
        try {
            if (!scheduler.checkExists(jobKey)) {
                return null;
            }
            TriggerKey tk = QuartzUtil.buildTriggerKey(jobKey);
            Trigger trigger = scheduler.getTrigger(tk);
            JobDetail jd = scheduler.getJobDetail(jobKey);
            return new JobEntity(jobKey, trigger, jd);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void resume(JobKey jobKey) {
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public boolean delete(JobKey jobKey) {
        try {
            if (scheduler.checkExists(jobKey)) {
                return scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    
    @Override
    public boolean delete(String jobName, String jobGroup) {
        return delete(JobKey.jobKey(jobName, jobGroup));
    }

    @Override
    public void clear() {
        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public boolean exist(String jobName, String jobGroup) {
        return exist(JobKey.jobKey(jobName, jobGroup));
    }
    
    @Override
    public boolean exist(JobKey jobKey) {
        try {
            return scheduler.checkExists(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void pause(JobKey jobKey) {
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pause(String jobName, String jobGroup) {
        pause(JobKey.jobKey(jobName, jobGroup));
    }

    @Override
    public void resume(String jobName, String jobGroup) {
        resume(JobKey.jobKey(jobName, jobGroup));
    }
    
    @Override
    public void interrupt(JobKey jobKey) {
        try {
            scheduler.interrupt(jobKey);
        } catch (UnableToInterruptJobException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TriggerState getState(TriggerKey triggerKey) {
        try {
            return scheduler.getTriggerState(triggerKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
    
}
