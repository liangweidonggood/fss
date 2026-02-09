package com.lwd.admin.config;

import org.springframework.stereotype.Component;

/**
 * 自定义 配置
 *
 * @author lwd
 */
@Component
public class CustomConfigHolder {

    private static CustomConfigProperties config;

    /**
     * 构造方法，用于初始化配置
     */
    protected CustomConfigHolder(CustomConfigProperties properties) {
        init(properties);
    }

    private static void init(CustomConfigProperties properties) {
        config = properties;
    }

    /**
     * 供全局静态工具类调用
     */
    public static CustomConfigProperties getConfig() {
        if (config == null) {
            throw new IllegalStateException("配置尚未初始化，请确保在 Spring 容器启动后调用");
        }
        return config;
    }
}
