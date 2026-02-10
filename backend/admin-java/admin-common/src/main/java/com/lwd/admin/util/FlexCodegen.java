package com.lwd.admin.util;

import com.lwd.admin.base.BaseEntity;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.TableConfig;
import com.mybatisflex.codegen.dialect.IDialect;
import com.mybatisflex.codegen.dialect.JdbcTypeMapping;
import com.mybatisflex.spring.service.impl.CacheableServiceImpl;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * mybatis-flex 代码生成
 * <a href="https://mybatis-flex.com/zh/others/codegen.html">官方文档</a>
 *
 * @author lwd
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FlexCodegen {

    /**
     * 代码生成
     *
     * @param data 代码生成数据
     */
    public static void generate(FlexCodegenData data) {
        registerPostgreSqlDateTypeMapping();
        GlobalConfig globalConfig = configure()
                .setPackageConfig(data.getProjectModulePath(), data.getBasePackage())
                .setTableConfig(data.getTablePrefix(), data.getSchema(), data.getTableNames())
                .setEntityConfig()
                .setMapperConfig()
                .setMapperXmlConfig(data.getProjectModulePath(), data.getBasePackage())
                .setServiceConfig()
                .setServiceImplConfig()
                .setControllerConfig()
                .build()
                ;
        generate(globalConfig, data.getJdbcUrl(), data.getJdbcUsername(), data.getJdbcPassword());
    }

    private static void generate(GlobalConfig globalConfig, String... dbConfigs) {
        try (HikariDataSource dataSource = new HikariDataSource()) {
            dataSource.setJdbcUrl(dbConfigs[0]);
            dataSource.setUsername(dbConfigs[1]);
            dataSource.setPassword(dbConfigs[2]);
            Generator generator = new Generator(dataSource, globalConfig, IDialect.POSTGRESQL);
            generator.generate();
        } catch (Exception e) {
            log.error("代码生成异常", e);
        }
    }

    private static ConfigLoader configure() {
        return new ConfigLoader();
    }

    /**
     * 配置加载器
     */
    private static final class ConfigLoader {
        private final GlobalConfig globalConfig = new GlobalConfig();

        private GlobalConfig build() {
            return this.globalConfig;
        }

        private ConfigLoader setPackageConfig(String projectModulePath, String basePackage) {
            String sourceDir = projectModulePath + "/src/main/java";
            globalConfig.getPackageConfig()
                        .setSourceDir(sourceDir)
                        .setBasePackage(basePackage)
            ;
            return this;
        }

        private ConfigLoader setTableConfig(String tablePrefix, String schema, Set<String> generateTable) {
            globalConfig.getStrategyConfig()
                        .setTablePrefix(tablePrefix)
                        .setGenerateTable(generateTable.toArray(new String[0]))
            ;
            Map<String, TableConfig> tableConfigMap = HashMap.newHashMap(16);
            for (String table : generateTable) {
                TableConfig tableConfig = TableConfig.create();
                tableConfig.setTableName(table);
                tableConfig.setSchema(schema);
                tableConfigMap.put(table, tableConfig);
            }
            globalConfig.setTableConfigMap(tableConfigMap);
            return this;
        }

        private ConfigLoader setEntityConfig() {
            globalConfig.enableEntity()
                        .setWithLombok(true)
                        .setJdkVersion(21)
                        .setOverwriteEnable(true)
                        .setSuperClass(BaseEntity.class)
            ;
            return this;
        }

        private ConfigLoader setMapperConfig() {
            globalConfig.enableMapper();
            return this;
        }

        private ConfigLoader setMapperXmlConfig(String projectModulePath, String basePackage) {
            String dirSplit = "/";
            String mapperXmlBasePath = projectModulePath + "/src/main/resources/mapper";
            String mapperXmlPath = mapperXmlBasePath + dirSplit + basePackage.replace(".", "/");
            globalConfig.enableMapperXml();
            globalConfig.setMapperXmlPath(mapperXmlPath);
            return this;
        }

        private ConfigLoader setServiceConfig() {
            globalConfig.enableService();
            return this;
        }

        private ConfigLoader setServiceImplConfig() {
            globalConfig.enableServiceImpl()
                        .setSuperClass(CacheableServiceImpl.class)
                        .setCacheExample(true)
            ;
            return this;
        }

        private ConfigLoader setControllerConfig() {
            globalConfig.enableController();
            return this;
        }
    }





    private static void registerPostgreSqlDateTypeMapping() {
        JdbcTypeMapping.registerMapping(Date.class, LocalDate.class);
        JdbcTypeMapping.registerMapping(Time.class, LocalTime.class);
        JdbcTypeMapping.registerMapping(Timestamp.class, LocalDateTime.class);
    }
}
