/*
 * Copyleft
 */
package com.wong.schedule.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 黄小天
 * @date 2019-02-21 11:33
 * @version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.wong.schedule")
@MapperScan(basePackages = "com.wong.**.mapper")
@EnableScheduling
@EnableAsync
public class ScheduleServer {
    
    public static void main(String[] args) {
        SpringApplication.run(ScheduleServer.class, args);
        System.out.println("ScheduleServer started!");
    }

}
