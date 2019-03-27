/*
 * Copyleft
 */
package com.wong.schedule.core.quartz.dao;

import org.springframework.data.repository.CrudRepository;

import com.wong.schedule.core.quartz.entity.JobEntity;

/**
 * qrtz_schedule_job 表的基本增删改查 dao 
 * @author 黄小天
 * @date 2019-02-24 09:54
 * @version 1.0
 */
public interface JobEntityDao extends CrudRepository<JobEntity, String> {

}
