/*
 * Copyleft
 */
package com.wong.schedule.module.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.wong.schedule.core.quartz.annotation.QuartzJob;

/**
 * {@link #QuartzJob} 注解测试类
 * @author 黄小天
 * @date 2019-02-22 23:27
 * @version 1.0
 */
@Component
@QuartzJob(fixedRate = 1, name = "jobname")
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class TestQuartzJob extends QuartzJobBean {
    
    private  static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Object value = context.getJobDetail().getJobDataMap().get("count");
        int count = 0;
        if (null != value) {
            count = context.getJobDetail().getJobDataMap().getInt("count");
        }
        context.getJobDetail().getJobDataMap().put("count", ++count);
        System.out.println("@QuartzJob 测试，时间：" + dateFormat.format(new Date()) 
            + "  线程ID：" + Thread.currentThread().getId()
            + ", job 已运行次数: " + count);
    }

}
