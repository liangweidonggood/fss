package com.lwd.admin.config.cache;

import com.lwd.admin.config.SchemaContextHolder;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;

/**
 * 自定义Redis序列化器
 *
 * @author lwd
 */
public class DynamicPrefixStringSerializer extends StringRedisSerializer {
    @Override
    public byte[] serialize(@Nullable String string) {
        String schema = SchemaContextHolder.getCurrentSchema();
        String prefix = (schema != null && !schema.isEmpty()) ? schema + ":" : "";
        return super.serialize(prefix + string);
    }
}
