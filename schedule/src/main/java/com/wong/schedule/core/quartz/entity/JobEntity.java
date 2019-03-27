/*
 * Copyleft
 */
package com.wong.schedule.core.quartz.entity;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wong.schedule.core.quartz.annotation.QuartzJob;
import com.wong.schedule.core.quartz.util.QuartzUtil;

/**
 * qrtz_schedule_job 表的实体类
 * @author 黄小天
 * @date 2019-02-23 23:08
 * @version 1.0
 */
@Entity
@Table(name = "qrtz_schedule_job")
public class JobEntity {
    @Id
    private String jobName;
    private String jobGroup;
    
    /** cron 表达式 */
    private String cron;
    
    /** 按照指定频率运行，单位：秒 */
    private Integer fixedRate;
    
    /** 总运行次数, 如果小于1或者为空, 代表永久运行 */
    private Integer count;
    
    /** 在 job 初始化完成后，延迟执行任务，单位：秒 */
    private Long initialDelay;
    
    /** 可以是 {@link QuartzJobBean} 的实现类的 className */
    private String jobClass;
    
    /** 
     * 如果 job 属性 {@link QuartzJobBean} 的实现类，那么当前 param 属性可以是一个 dataMap，</br>
     * 在 job 初始化的时候将会放在 context 中</br></br>
     */
    private String param;
    
    /** 为 true 时，该任务才允许执行 */
    private Boolean status;
    
    /** 当前 job 的解释 */
    private String comment;
    
    public JobEntity() {}
    
    public JobEntity(JobKey jobKey, Trigger trigger, JobDetail jobDetail) {
        setJobKey(jobKey);
        setTrigger(trigger);
        jobClass = jobDetail.getJobClass().getName();
        JobDataMap dataMap = jobDetail.getJobDataMap();
        if (dataMap != null) {
            try {
                param = new ObjectMapper().writeValueAsString(dataMap);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
    
    public JobEntity(QuartzJob anno, String jobClass) {
        setCount(anno.count());
        setCron(anno.cron());
        setFixedRate(anno.fixedRate());
        setInitialDelay(anno.initialDelay());
        setJobName(anno.name());
        setJobGroup(anno.group());
        setParam(anno.param());
        setJobClass(jobClass);
        setStatus(anno.status());
    }
    
    public void setJobKey(JobKey jobKey) {
        setJobName(jobKey.getName());
        setJobGroup(jobKey.getGroup());
    }
    
    public JobKey getJobKey() {
        return JobKey.jobKey(jobName, jobGroup);
    }
    
    public JobDetail getJobDetail() {
        try {
            return QuartzUtil.buildJobDetail(getJobKey(), getJobClazz(), getData());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 创建定时任务的 trigger， 用于和 jobDetail 绑定
     * @return
     */
    public Trigger getTrigger() {
        if (cron == null || cron.equals("")) {
            return QuartzUtil.buildSimpleTrigger(jobName, jobGroup, fixedRate, count, initialDelay);
        } else {
            return QuartzUtil.buildCronTrigger(jobName, jobGroup, cron);
        }
    }

    public void setTrigger(Trigger trigger) {
        if (trigger instanceof CronTrigger) {
            cron = ((CronTrigger)trigger).getCronExpression();
        } else if (trigger instanceof SimpleTrigger) {
            SimpleTrigger simpleTrigger = (SimpleTrigger)trigger;
            fixedRate = new Long(simpleTrigger.getRepeatInterval()).intValue();
            count = simpleTrigger.getRepeatCount();
        }
    }
    
    public JobDataMap getData() {
        if (param != null && !"".equals(param)) {
            try {
                return new ObjectMapper().readValue(param, JobDataMap.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    
    public Class<?> getJobClazz() {
        if (jobClass != null) {
            try {
                return Class.forName(jobClass);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("job class must be not null");
    }
    
    public TriggerKey getTriggerKey() {
        return new TriggerKey(jobName, jobGroup);
    }
    
    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        if (StringUtils.isEmpty(jobGroup)) {
            jobGroup = Scheduler.DEFAULT_GROUP;
        }
        this.jobGroup = jobGroup;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getFixedRate() {
        return fixedRate;
    }

    public void setFixedRate(Integer fixedRate) {
        this.fixedRate = fixedRate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(Long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String job) {
        this.jobClass = job;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    @Override  
    public String toString() {  
        return "JobEntity ["
                + "jobName=" + jobName 
                + ", jobGroup=" + jobGroup 
                + ", cron=" + cron 
                + ", fixedRate=" + fixedRate 
                + ", count=" + count 
                + ", initialDelay=" + initialDelay 
                + ", jobClass=" + jobClass 
                + ", param=" + param 
                + ", status=" + status 
                + "]";  
    }
    
}
