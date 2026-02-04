package com.lwd.admin.config;

import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.mybatis.FlexConfiguration;
import com.mybatisflex.spring.boot.ConfigurationCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * Flex 配置.
 *
 * @author lwd
 */
@Slf4j
@Configuration
public class FlexConfig implements ConfigurationCustomizer {

    @Override
    public void customize(FlexConfiguration flexConfiguration) {
        // 开启审计功能
        AuditManager.setAuditEnable(true);
        // 设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage -> log.info("sql:{},exec:{}ms", auditMessage.getFullSql(),
                auditMessage.getElapsedTime()));
    }
}
