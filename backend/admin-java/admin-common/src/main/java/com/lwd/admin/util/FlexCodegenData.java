package com.lwd.admin.util;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * 代码生成数据
 *
 * @author lwd
 */
@Builder
@Data
public class FlexCodegenData {
    /** 数据库连接地址 */
    private String jdbcUrl;
    /** 数据库用户名 */
    private String jdbcUsername;
    /** 数据库密码 */
    private String jdbcPassword;
    /** 项目模块路径 */
    private String projectModulePath;
    /** 基础包名 */
    private String basePackage;
    /** 表前缀 */
    private String tablePrefix;
    /** 数据库模式 */
    private String schema;
    /** 需要生成的表 */
    private Set<String> tableNames;
}
