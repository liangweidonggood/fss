package com.lwd.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 自定义配置
 *
 * @author lwd
 */
@ConfigurationProperties(prefix = "custom-config")
public record CustomConfigProperties(
        Common common,
        MultiTenant multiTenant
) {
    /** 通用配置 */
    public record Common(
            List<String> excludeUrls
    ){}

    /** MultiTenant 配置 */
    public record MultiTenant(
            boolean enable,
            String defaultSchema,
            String adminAddress,
            String domain
    ) {}
}
