/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : schedule

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-03-11 16:55:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for gsms_schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_schedule_job`;
CREATE TABLE `qrtz_schedule_job` (
  `job_name` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '任务名称',
  `job_group` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '任务所在分组',
  `cron` varchar(40) CHARACTER SET utf8 DEFAULT NULL COMMENT 'cron 表达式',
  `fixed_rate` int(11) DEFAULT NULL COMMENT 'job 运行频率，单位：秒',
  `count` int(11) DEFAULT NULL COMMENT '总运行次数, 如果小于1或者为空, 代表永久运行',
  `initial_delay` bigint(20) DEFAULT NULL COMMENT '在 job 初始化完成后，延迟执行任务，单位：秒',
  `job_class` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT 'QuartzJobBean 的实现类的 className',
  `param` varchar(1024) CHARACTER SET utf8 DEFAULT NULL COMMENT '要传递给 job 的参数',
  `status` tinyint(4) DEFAULT NULL COMMENT '为 true 时，该任务才允许执行',
  `comment` varchar(1024) CHARACTER SET utf8 DEFAULT NULL COMMENT '注释、任务说明',
  PRIMARY KEY (`job_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
