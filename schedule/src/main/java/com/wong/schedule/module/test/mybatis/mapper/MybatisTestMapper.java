/*
 * Copyleft
 */
package com.wong.schedule.module.test.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author 黄小天
 * @date 2019-03-12 11:33
 * @version 1.0
 */
public interface MybatisTestMapper {
    
    String findEntity(@Param("jobName") String jobName);

}
