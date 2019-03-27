/*
 * Copyleft
 */
package com.wong.schedule.module.test;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wong.schedule.module.test.mybatis.service.MybatisTestService;

/**
 * mvv 相关测试类
 * @author 黄小天
 * @date 2019-02-21 11:29
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class TestController {
    
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);
    
    @Resource
    private MybatisTestService testService;
    
    @RequestMapping("/findEntity")
    public Object findEntity() {
        LOG.info("findEntity");
        return testService.findEntity("jobname");
    }

}
