package com.lwd.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义配置
 *
 * @author lwd
 */
@ConfigurationProperties(prefix = "custom-config")
public record CustomConfigProperties(
        MultiTenant multiTenant
) {
    /** MultiTenant 配置 */
    public record MultiTenant(
            boolean enable,
            String defaultSchema,
            String adminAddress,
            String domain
    ) {}
}
