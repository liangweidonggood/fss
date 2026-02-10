package com.lwd.admin.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 数据库字段常量
 *
 * @author lwd
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S2094")
public class DbColumn {
    public static final String VERSION = "id";
    public static final String IS_DELETED = "is_deleted";
}
