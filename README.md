# SpringBoot-Quartz-Mybatis
##### 前言：
由于最近公司要做重构，定时任务模块全部抽离出来，所以要搭建一个定时任务模块，于是就研究了一下，不过到最后用的并不是本文搭建的项目，由于公司原来项目架构原因和其他因素影响，定时任务只能当做一个子模块独立出来，做这个的原因是为了代码更加清晰明了和支持分布式而已，最后还是要引用到原来的系统里面的，所以最后并没有使用自己搭建的这个项目，只用到了里面一少部分核心代码，并且为了兼容原来的系统，改动也不小。

造轮子的文章本来没什么好写的，网上一大堆，不过还是当做总结来写一下吧！ 虽然说是 SpringBoot 整合 Quartz + Mybatis 但这里主要是 SpringBoot 整合 Quartz 功能，Mybatis 打辅助，下面就来看看里面的东东。

**文件目录结构图：**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190327221313153.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29jcDExNA==,size_16,color_FFFFFF,t_70)
**定时任务管理页面：**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190327221504202.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29jcDExNA==,size_16,color_FFFFFF,t_70)
**技术选型：**
 1. SpringBoot 2.1.3
 2. Quartz 2.3
 3. Mybatis 3.5
 4. ~~redis客户端~~ （已经整合了，但出于简单考虑，代码已经被删掉）
 5. druid 1.1.14
 6. fastjson 1.2.56
 7. bootstrap 4.3.1
 8. angularjs 1.5.8
 9. jquery 3.3.1

**功能点：**
 1. 采用自定义注解的方式来完成定时任务配置，类似 Spring 的 @Scheduled
 2. 可以在网页查看、暂停、启动某个定时任务（限被自定义注解的定时任务）
 3. Mybatis 相关操作大家都知道
 4. druid 管理页面配置（纯粹觉得没东西写就把这个也拼凑上来o(*￣▽￣*)o

**使用方式：**
 1. 创建数据库
 2. 执行 quartz_innodb.sql 创建 quartz 依赖表
 3. 执行 qrtz_schedule_job.sql 创建这个框架的任务管理依赖表
 4. 修改 application.yml 的数据库连接属性
 5. 看看 TestQuartzJob 和 TestSpringScheduled 两个 class 的实现方式
 6. 启动 ScheduleServer
 7. 定时任务管理界面 http://localhost:9090/views/schedule.html
 8. druid 管理页面 http://localhost:9090/druid/index.html 账号密码在 application.yml 配置文件中
 9. 要验证多节点的话复制项目出来改一下端口启动就行，如果时间多可以装虚拟鸡或者在其他电脑一起跑看看下面图片中各个节点打印出来的异同 \~(￣▽￣)\~*
 
**启动后会看到这个：**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190327222656353.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29jcDExNA==,size_16,color_FFFFFF,t_70)
**最后：**
感谢不幸点进来各位兄dei，项目看看就好了，在公司那个模块搭好后发现这个框架还有很多可以完善的地方，不过出于公司代码原因就不加到这里了，例如：
 1. QuartzJob 注解的属性还可以更加完善，不然有些属性配置对接不了 quartz
 2. 在定时任务初始化的时候还应该做更多判断和有更多更好的处理方法，例如 qrtz_schedule_job 表不创建也照样能跑等
 3. 项目代码结构可以调整一下，现在是有点分工还不够清晰
 4. 还有很多很多.......就不多说了，认真起来得一行一行 review 一下
