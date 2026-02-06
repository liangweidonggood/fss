package com.lwd.admin.config.flex;

import com.lwd.admin.config.SchemaContextHolder;
import com.mybatisflex.core.dialect.OperateType;
import com.mybatisflex.core.table.DynamicSchemaProcessor;

/**
 * 自定义动态模式
 *
 * @author lwd
 */
public class MyDynamicSchemaProcessor implements DynamicSchemaProcessor {

    @Override
    public String process(String schema, String table, OperateType operateType) {
        String result = table;
        String currentSchema = SchemaContextHolder.getCurrentSchema();
        if (currentSchema != null && !currentSchema.isEmpty()) {
            result = currentSchema + "." + table;
        }
        return result;
    }
}
