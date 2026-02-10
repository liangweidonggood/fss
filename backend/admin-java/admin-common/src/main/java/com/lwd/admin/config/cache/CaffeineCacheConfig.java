package com.lwd.admin.config.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * caffeine缓存配置 适用于本应用高性能缓存，如果跨应用使用请使用redis缓存
 * 本地缓存要自行处理多租户数据隔离问题
 *
 * @author lwd
 */
@Configuration
public class CaffeineCacheConfig {
    /**
     * 直接操作缓存对象 直接注入后操作缓存get put 等方法
     *
     * @return Cache
     */
    @Bean("shortTermCache")
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                .recordStats()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(60, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(256)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

    /**
     * 永久缓存
     *
     * @return Cache
     */
    @Bean("permanentCache")
    public Cache<String, Object> permanentCache() {
        return Caffeine.newBuilder().recordStats().initialCapacity(256).maximumSize(1000).build();
    }
}
