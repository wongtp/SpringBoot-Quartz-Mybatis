/*
 * Copyleft
 */
package com.wong.schedule.core.quartz.service;

import org.quartz.JobKey;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;

import com.wong.schedule.core.quartz.entity.JobEntity;

/**
 * 和 quartz 交互的 service
 * @author 黄小天
 * @date 2019-02-23 23:06
 * @version 1.0
 */
public interface QuartzService {
    
    /**
     * 新增一个 job, 如果存在就覆盖
     */
    void add(JobEntity entity);
    
    /**
     * 获取一个已经被注册到 quartz 的 JobEntity
     * @param jobName 
     * @param jobGroup
     * @return
     */
    JobEntity fetch(String jobName, String jobGroup);
    
    /**
     * 获取一个已经被注册到 quartz 的 JobEntity
     * @param jobKey 
     * @return
     */
    JobEntity fetch(JobKey jobKey);
    
    /**
     * 获取一个 job 的状态
     */
    TriggerState getState(TriggerKey triggerKey);


    /**
     * 删除一个 job
     */
    boolean delete(JobKey jobKey);
    
    /**
     * 删除一个 job
     */
    boolean delete(String jobName, String jobGroup);
    
    /**
     * 清除所有 job
     */
    void clear();

    /**
     * 判断是否存在某个 job
     */
    boolean exist(JobKey jobKey);
    
    /**
     * 判断是否存在某个 job
     */
    boolean exist(String jobName, String jobGroup);
    
    /**
     * 暂停一个 job
     */
    void pause(JobKey jobKey);
    
    /**
     * 暂停一个 job
     */
    void pause(String jobName, String jobGroup);

    /**
     * 恢复一个被暂停的 job
     */
    void resume(JobKey jobKey);
    
    /**
     * 恢复一个被暂停的 job
     */
    void resume(String jobName, String jobGroup);

    /**
     * 触发一个中断, 该 job 类必须实现  InterruptableJob 接口
     */
    void interrupt(JobKey jobKey);

}