package com.lwd.admin;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 启动类.
 *
 * @author lwd
 */
@Slf4j
@MapperScan("com.lwd.admin.**.mapper")
@SpringBootApplication
@ConfigurationPropertiesScan("com.lwd.admin")
public class AdminBootApplication {

    private static ConfigurableEnvironment globalEnv;

    /**
     * 启动方法.
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        SpringApplication app = new SpringApplication(AdminBootApplication.class);
        app.addListeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
            globalEnv = event.getEnvironment();
        });
        app.run(args);
        long endTime = System.currentTimeMillis();
        long startupTime = endTime - startTime;
        String appName = globalEnv.getProperty("spring.application.name");
        String serverPort = globalEnv.getProperty("server.port");
        boolean isApiEnabled = globalEnv.getProperty("springdoc.api-docs.enabled", Boolean.class, true);
        boolean isKnife4jEnabled = globalEnv.getProperty("knife4j.enable", Boolean.class, true);
        log.info("""
                    \r----------------------------------------------------------
                    Application '{}' is running Success!
                    spring.config.activate.on-profile: {}
                    springdoc.api-docs.enabled: {}
                    knife4j.enable: {}
                    接口文档访问地址: http://localhost:{}/doc.html
                    启动耗时: {} ms
                    ----------------------------------------------------------
                """, appName, globalEnv.getActiveProfiles(), isApiEnabled, isKnife4jEnabled, serverPort, startupTime);
    }
}
