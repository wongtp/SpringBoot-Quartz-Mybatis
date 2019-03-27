/*
 * Copyleft
 */
package com.wong.schedule.core.quartz;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.wong.schedule.core.quartz.annotation.QuartzJob;
import com.wong.schedule.core.quartz.dao.JobEntityDao;
import com.wong.schedule.core.quartz.entity.JobEntity;

/**
 * 负责初始化所有被 {@link #QuartzJob} 注解的定时任务
 * @author 黄小天
 * @date 2019-02-22 23:30
 * @version 1.0
 */
@Component
public class QuartzJobManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(QuartzJobManager.class);
    
    @Autowired
    private ApplicationContext context;
    @Autowired
    private SchedulerFactoryBean schedulerFactory;
    @Autowired
    private JobEntityDao dao;
    
    private Scheduler scheduler;
    
    @PostConstruct
    private void init() throws Exception {
        scheduler = schedulerFactory.getScheduler();
        initAllJob();
    }
    
    /**
     * 初始化所有被 {@link #QuartzJob} 注解的 job
     * @throws Exception
     */
    private void initAllJob() throws Exception {
        List<Object> jobList = this.getAllJobBean();
        for (Object bean : jobList) {
            QuartzJob anno = bean.getClass().getAnnotation(QuartzJob.class);
            Optional<JobEntity> option = dao.findById(anno.name());
            // 如果数据库没有改 job 的信息，则入库
            JobEntity entity = null;
            try {
                if (!option.isPresent()) {
                    entity = new JobEntity(anno, bean.getClass().getName());
                    dao.save(entity);
                } else {
                    entity = option.get();
                }
                // 启动 job
                if (entity.getStatus() && !scheduler.checkExists(entity.getJobKey())) {
                    scheduler.scheduleJob(entity.getJobDetail(), entity.getTrigger());
                }
            } catch (Exception e) {
                String msg = String.format("catch exception while init job, %s"
                        + "it may be cause by job config error, please checked!", entity);
                LOG.error(msg, e);
                throw e;
            }
        }
    }
    
    /**
     * 查询所有被 {@link QuartzJob} 注解的 job bean
     * @return
     */
    private List<Object> getAllJobBean() {
        Map<String, Object> jobMap = context.getBeansWithAnnotation(QuartzJob.class);
        Iterator<Object> it = jobMap.values().iterator();
        List<Object> jobList = new ArrayList<>();
        while (it.hasNext()) {
            Object object = it.next();
            if (object instanceof Job) {
                jobList.add(object);
            }
        }
        return jobList;
    }
}
