package com.lwd.admin.config;

/**
 * schema上下文
 *
 * @author lwd
 */
public final class SchemaContextHolder {
    private static final ThreadLocal<String> SCHEMA_THREAD_LOCAL = new InheritableThreadLocal<>();

    private SchemaContextHolder() {}

    /**
     * 设置当前schema
     *
     * @param schemaName schema名称
     */
    public static void setCurrentSchema(String schemaName) {
        SCHEMA_THREAD_LOCAL.set(schemaName);
    }

    /**
     * 获取当前schema
     *
     * @return schema名称
     */
    public static String getCurrentSchema() {
        return SCHEMA_THREAD_LOCAL.get();
    }

    /**
     * 清空当前schema
     */
    public static void clear() {
        SCHEMA_THREAD_LOCAL.remove();
    }
}
