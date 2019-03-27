package com.wong.schedule;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wong.schedule.core.ScheduleServer;

/**
 * 
 * @author 黄小天
 * @date 2019-03-12 08:22
 * @version 1.0
 */
@SpringBootTest(classes = ScheduleServer.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestDataSource {
    
    @Resource
    private DataSource dataSource;
    
    @Test
    public void testConnection() throws Exception {
        System.out.println(this.dataSource);
    }
}
