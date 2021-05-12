package com.omni.job.admin;

import com.jd.spring.boot.autoconfigure.erp.ErpAutoConfiguration;
import com.jd.spring.boot.autoconfigure.jimdb.JimDBAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = {JimDBAutoConfiguration.class, ErpAutoConfiguration.class})
@ImportResource("classpath:spring/spring-root.xml")
public class OmniJobAdminApplication {

    private static final Logger logger = LoggerFactory.getLogger(OmniJobAdminApplication.class);

	public static void main(String[] args) {
        SpringApplication.run(OmniJobAdminApplication.class, args);
        logger.info("***************************** omni任务调度系统启动完成 *****************************");
	}

}