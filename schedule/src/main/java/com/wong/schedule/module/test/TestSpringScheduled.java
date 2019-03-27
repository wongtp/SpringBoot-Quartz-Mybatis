/*
 * Copyleft
 */
package com.wong.schedule.module.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * spring {@link #Scheduled} 注解测试类
 * @author 黄小天
 * @date 2019-02-22 08:58
 * @version 1.0
 */
@Component
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class TestSpringScheduled {
    
    private  static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    @Scheduled(fixedRate = 3000)
    public void reportCurrentTime() {
        System.out.println("@Scheduled 测试，时间：" + dateFormat.format(new Date()) + "  线程ID：" + Thread.currentThread().getId());
    }
}
