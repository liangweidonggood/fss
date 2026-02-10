package com.lwd.admin.config.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.lwd.admin.config.SchemaContextHolder;
import com.lwd.admin.constant.DatePattern;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Redis配置
 *
 * @author lwd
 */
@Configuration
public class RedisCacheConfig {


    /**
     * 配置Jackson2序列化器
     */
    private Jackson2JsonRedisSerializer<Object> createJacksonSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 设置可见性
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 支持自动类型识别
        objectMapper.activateDefaultTyping(
                objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.DATETIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DatePattern.DATETIME_FORMATTER));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DatePattern.DATE_FORMATTER));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DatePattern.DATE_FORMATTER));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DatePattern.TIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DatePattern.TIME_FORMATTER));
        // 支持JSR310时间类型
        objectMapper.registerModule(javaTimeModule);
        // 禁用写入日期为时间戳
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 使用构造函数传入ObjectMapper，避免过时警告
        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }

    /**
     * RedisTemplate配置，使用Jackson2进行序列化
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 创建序列化器
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = createJacksonSerializer();
        DynamicPrefixStringSerializer dynamicSerializer = new DynamicPrefixStringSerializer();

        // key使用String序列化器
        redisTemplate.setKeySerializer(dynamicSerializer);
        // value使用Jackson2序列化器
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        // hash key使用String序列化器
        redisTemplate.setHashKeySerializer(dynamicSerializer);
        // hash value使用Jackson2序列化器
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * Redis缓存管理器配置
     * 缓存过期时间默认1小时
     * 对注解缓存生效
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 创建序列化器
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = createJacksonSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();


        // Redis缓存配置
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                // 动态生成缓存前缀
                .computePrefixWith(cacheName -> {
                    String schema = SchemaContextHolder.getCurrentSchema();
                    return (schema != null ? schema + ":" : "") + cacheName + ":";
                })
                // 设置缓存过期时间（默认1小时）
                .entryTtl(Duration.ofHours(1))
                // 设置key序列化器
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                stringRedisSerializer))
                // 设置value序列化器
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                jackson2JsonRedisSerializer))
                // 禁用缓存空值
                .disableCachingNullValues()
                ;
        // 创建Redis缓存管理器
        return RedisCacheManager.builder(redisConnectionFactory)
                                // 设置默认缓存配置
                                .cacheDefaults(cacheConfiguration)
                                // 启用缓存配置的统计
                                .enableStatistics()
                                .build();
    }
}
