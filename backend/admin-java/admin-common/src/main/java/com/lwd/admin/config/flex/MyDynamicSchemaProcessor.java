package com.lwd.admin.config.flex;

import com.lwd.admin.config.SchemaContextHolder;
import com.mybatisflex.core.dialect.OperateType;
import com.mybatisflex.core.table.DynamicSchemaProcessor;
import org.apache.commons.lang3.StringUtils;

/**
 * 自定义动态模式
 *
 * @author lwd
 */
public class MyDynamicSchemaProcessor implements DynamicSchemaProcessor {

    @Override
    public String process(String schema, String table, OperateType operateType) {
        String result = schema;
        String currentSchema = SchemaContextHolder.getCurrentSchema();
        if (StringUtils.isNotBlank(currentSchema)) {
            result = currentSchema;
        }
        return result;
    }
}
