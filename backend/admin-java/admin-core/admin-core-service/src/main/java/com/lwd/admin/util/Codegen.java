package com.lwd.admin.util;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.ColumnConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * mybatis-flex 代码生成
 * <a href="https://mybatis-flex.com/zh/others/codegen.html">官方文档</a>
 * @author lwd
 */
public class Codegen {
    public static void main(String[] args) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres?currentSchema=cicp_admin");
        dataSource.setUsername("postgres");
        dataSource.setPassword("1234");
        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle1();
        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);
        //生成代码
        generator.generate();
    }
    /**
     * 创建配置内容
     *
     */
    public static GlobalConfig createGlobalConfigUseStyle1() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置源代码目录
        globalConfig.setSourceDir("D:\\workspace\\github\\fss\\backend\\admin-java\\admin-core\\admin-core-service\\src\\main\\java");

        //设置根包
        globalConfig.setBasePackage("com.lwd.admin");

        //设置表前缀和只生成哪些表
        globalConfig.setTablePrefix("auth_");
        globalConfig.setGenerateTable("auth_user");

        //设置生成 entity 并启用 Lombok
        globalConfig.setEntityGenerateEnable(true);
        globalConfig.setEntityWithLombok(true);
        //设置项目的JDK版本，项目的JDK为14及以上时建议设置该项，小于14则可以不设置
        globalConfig.setEntityJdkVersion(21);

        //设置生成 mapper
        globalConfig.setMapperGenerateEnable(true);

        //启用 Service 生成
        globalConfig.enableService();
        //启用 ServiceImpl 生成
        globalConfig.enableServiceImpl();
        //启用 Controller 生成
        globalConfig.enableController();
        //启用 TableDef 生成
        globalConfig.enableTableDef();
        //启用 MapperXml 生成
        globalConfig.enableMapperXml();
        globalConfig.setMapperXmlFilePrefix("com.lwd.admin");
        globalConfig.setMapperXmlPath("D:\\workspace\\github\\fss\\backend\\admin-java\\admin-core\\admin-core-service\\src\\main\\resources");


        return globalConfig;
    }

    public static GlobalConfig createGlobalConfigUseStyle2() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置根包
        globalConfig.getPackageConfig()
                .setBasePackage("com.test");

        //设置表前缀和只生成哪些表，setGenerateTable 未配置时，生成所有表
        globalConfig.getStrategyConfig()
                .setTablePrefix("tb_")
                .setGenerateTable("tb_account", "tb_account_session");

        //设置生成 entity 并启用 Lombok
        globalConfig.enableEntity()
                .setWithLombok(true)
                .setJdkVersion(17);

        //设置生成 mapper
        globalConfig.enableMapper();

        //可以单独配置某个列
        ColumnConfig columnConfig = new ColumnConfig();
        columnConfig.setColumnName("tenant_id");
        columnConfig.setLarge(true);
        columnConfig.setVersion(true);
        globalConfig.getStrategyConfig()
                .setColumnConfig("tb_account", columnConfig);

        return globalConfig;
    }
}
