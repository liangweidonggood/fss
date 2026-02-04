package com.lwd.admin.util;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.dialect.JdbcTypeMapping;
import com.mybatisflex.codegen.template.impl.EnjoyTemplate;
import com.mybatisflex.spring.service.impl.CacheableServiceImpl;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

/**
 * mybatis-flex 代码生成
 * <a href="https://mybatis-flex.com/zh/others/codegen.html">官方文档</a>
 *
 * @author lwd
 */
@Slf4j
public class Codegen {
    /**
     * 运行该方法生成代码.
     */
    public static void main(String[] args) {
        try (HikariDataSource dataSource = new HikariDataSource()) {
            dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres?currentSchema=cicp_admin" +
                    "&useInformationSchema=true");
            dataSource.setUsername("postgres");
            dataSource.setPassword("1234");
            registerPostgreSqlDateTypeMapping();
            String projectModulePath = "D:/workspace/github/fss/backend/admin-java/admin-core/admin-core-service";
            String basePackage = "com.lwd.admin.system";
            String tablePrefix = "auth_";
            Set<String> generateTable = Set.of("auth_user");
            GlobalConfig globalConfig = createGlobalConfig(projectModulePath, basePackage, tablePrefix, generateTable);
            Generator generator = new Generator(dataSource, globalConfig);
            generator.generate();
        } catch (Exception e) {
            log.error("代码生成异常", e);
        }
    }

    /**
     * 创建配置内容.
     */
    public static GlobalConfig createGlobalConfig(String projectModulePath, String basePackage, String tablePrefix,
                                                  Set<String> generateTable) {
        String dirSplit = "/";
        String sourceDir = projectModulePath + "/src/main/java";
        String mapperXmlBasePath = projectModulePath + "/src/main/resources/mapper";
        String mapperXmlPath = mapperXmlBasePath + dirSplit + basePackage.replace(".", "/");
        // 创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();
        // 设置根包
        globalConfig.getPackageConfig()
                    .setSourceDir(sourceDir)
                    .setBasePackage(basePackage)
        ;

        // 设置StrategyConfig
        globalConfig.getStrategyConfig()
                    .setTablePrefix(tablePrefix)
                    .setGenerateTable(generateTable.toArray(new String[0]))
        ;

        // 设置生成 entity 并启用 Lombok
        globalConfig.enableEntity()
                    .setWithLombok(true)
                    .setJdkVersion(21)
                    .setOverwriteEnable(true)
        ;

        // 设置生成 mapper
        globalConfig.enableMapper();

        // 启用 Service 生成
        globalConfig.enableService()
                    .setOverwriteEnable(true);

        // 启用 ServiceImpl 生成
        globalConfig.enableServiceImpl()
                    .setSuperClass(CacheableServiceImpl.class)
                    .setCacheExample(true)
                    .setOverwriteEnable(true)
        ;

        // 启用 Controller 生成
        globalConfig.enableController();

        // 启用 TableDef 生成
        globalConfig.enableTableDef()
                    .setOverwriteEnable(true);

        // 启用 MapperXml 生成
        globalConfig.enableMapperXml();
        globalConfig.setMapperXmlPath(mapperXmlPath);

        // 设置模板
        globalConfig.getTemplateConfig()
                    .setTemplate(new EnjoyTemplate())
                    .setController(projectModulePath + "/src" + "/main/resources/templates/controller.tpl")
                    .setTableDef(projectModulePath + "/src/main/resources" + "/templates/tableDef.tpl")
        ;
        return globalConfig;
    }

    private static void registerPostgreSqlDateTypeMapping() {
        JdbcTypeMapping.registerMapping(Date.class, LocalDate.class);
        JdbcTypeMapping.registerMapping(Time.class, LocalTime.class);
        JdbcTypeMapping.registerMapping(Timestamp.class, LocalDateTime.class);
    }
}
