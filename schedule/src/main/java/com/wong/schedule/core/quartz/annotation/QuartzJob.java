/*
 * Copyleft
 */
package com.wong.schedule.core.quartz.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解仅仅对于 qrtz_schedule_job 表中没有的对应任务 job 有用<br>
 * 该注解可以帮助开发人员直接在定时任务 Job 实现类上配置相关的任务信息<br>
 * 系统启动的时候，会加载有该注解 的 bean 的信息保存到  qrtz_schedule_job 表中
 * @author 黄小天
 * @date 2019-02-22 13:40
 * @version 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QuartzJob {
    
    /**
     * 任务名称，定义好后最好就不要改变了，因为这个会 quartz 任务的 jobName， job 的修改需要使用该值来定位 job
     */
    String name();
    
    /**
     * 任务所属分组名称，定义好后最好就不要改变了，因为这个会 quartz 任务的 jobName， job 的修改需要使用该值来定位 job
     */
    String group() default "";
    
    /**
     * 任务执行的 cron 的表达式
     */
    String cron() default "";
    
    /**
     * 按照指定频率运行，单位：秒
     */
    int fixedRate() default -1;
    
    /**
     * 总运行次数, 如果小于1, 代表永久运行
     */
    int count() default -1;
    
    /**
     * 任务延迟启动的时间，单位：秒
     */
    long initialDelay() default -1;
    
    /**
     * 要传递给被注解的 job 的参数，可通过 context.getJobDetail().getJobDataMap() 获取
     */
    String param() default "";
    
    /**
     * 任务默认状态，当新增任务时，系统会根据该配置项来判断是否要启动任务, true:启动，false：不启动
     */
    boolean status() default true;

}
