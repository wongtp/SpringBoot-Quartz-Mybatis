/*
 * Copyleft
 */
package com.wong.schedule.module.test.mybatis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wong.schedule.module.test.mybatis.mapper.MybatisTestMapper;
import com.wong.schedule.module.test.mybatis.service.MybatisTestService;

/**
 * 
 * @author 黄小天
 * @date 2019-03-12 11:46
 * @version 1.0
 */
@Service
public class MybatisTestServiceImpl implements MybatisTestService {
    
    @Autowired
    private MybatisTestMapper mapper;
    
    @Override
    public String findEntity(String jobName) {
        return mapper.findEntity(jobName);
    }

}
