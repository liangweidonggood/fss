package com.lwd.admin.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.lwd.admin.constant.DatePattern;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * LocalDateTime 格式配置
 *
 * @author lwd
 */
@Configuration
public class JacksonConfig {
    /**
     * 日期序列化
     *
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class,
                    new LocalDateTimeSerializer(DatePattern.DATETIME_FORMATTER));
            builder.deserializerByType(LocalDateTime.class,
                    new LocalDateTimeDeserializer(DatePattern.DATETIME_FORMATTER));
            builder.serializerByType(LocalDate.class,
                    new LocalDateSerializer(DatePattern.DATE_FORMATTER));
            builder.deserializerByType(LocalDate.class,
                    new LocalDateDeserializer(DatePattern.DATE_FORMATTER));
            builder.serializerByType(LocalTime.class,
                    new LocalTimeSerializer(DatePattern.TIME_FORMATTER));
            builder.deserializerByType(LocalTime.class,
                    new LocalTimeDeserializer(DatePattern.TIME_FORMATTER));
        };
    }
}
