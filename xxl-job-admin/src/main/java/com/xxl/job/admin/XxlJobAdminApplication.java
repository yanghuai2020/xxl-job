package com.xxl.job.admin;

import com.jd.spring.boot.autoconfigure.commonsupport.EasyUmpAutoConfiguration;
import com.jd.spring.boot.autoconfigure.commonsupport.LoggableAutoConfiguration;
import com.jd.spring.boot.autoconfigure.erp.ErpAutoConfiguration;
import com.jd.spring.boot.autoconfigure.jimdb.JimDBAutoConfiguration;
import com.jd.spring.boot.autoconfigure.jmq.JMQAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@SpringBootApplication(exclude = {JimDBAutoConfiguration.class, ErpAutoConfiguration.class})
@ImportResource("classpath:spring/spring-root.xml")
public class XxlJobAdminApplication {

    private static final Logger logger = LoggerFactory.getLogger(XxlJobAdminApplication.class);

	public static void main(String[] args) {
        SpringApplication.run(XxlJobAdminApplication.class, args);
        logger.info("***************************** omni任务调度系统启动完成 *****************************");
	}

}