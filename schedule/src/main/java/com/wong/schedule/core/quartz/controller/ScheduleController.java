/*
 * Copyleft
 */
package com.wong.schedule.core.quartz.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wong.schedule.core.quartz.dao.JobEntityDao;
import com.wong.schedule.core.quartz.entity.JobEntity;
import com.wong.schedule.core.quartz.service.QuartzService;

/**
 * 用于网页端请求查询定时任务信息和启动以及暂停任务
 * @author 黄小天
 * @date 2019-02-23 20:31
 * @version 1.0
 */
@RestController
@RequestMapping("/quartz")
public class ScheduleController {
    
    @Autowired
    private QuartzService quartzService;
    
    @Autowired
    private JobEntityDao dao;
    
    @RequestMapping(value = "/changeStatus")
    public Object changeStatus(@RequestParam(name = "jobName")String jobName, 
            @RequestParam(name = "jobGroup", required = false)String jobGroup,
            @RequestParam(name = "status")Boolean status) {
        if (status) {
            quartzService.resume(JobKey.jobKey(jobName, jobGroup));
        }else {
            quartzService.pause(JobKey.jobKey(jobName, jobGroup));
        }
        return true;
    }
    
    @RequestMapping("/fetchAllJob")
    public List<JobEntity> fetchAllJob() {
        // 全部查出来，不太可能有上千个定时任务吧，即使百来个都没问题
        Iterable<JobEntity> it = dao.findAll();
        Iterator<JobEntity> iterable = it.iterator();
        List<JobEntity> list = new ArrayList<>();
        while (iterable.hasNext()) {
            list.add(iterable.next());
        }
        return list;
    }
}
