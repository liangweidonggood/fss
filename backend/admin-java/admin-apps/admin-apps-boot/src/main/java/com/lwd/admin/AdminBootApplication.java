package com.lwd.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @author lwd
 */
@MapperScan("com.lwd.admin.**.mapper")
@SpringBootApplication
public class AdminBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminBootApplication.class, args);
    }
}
