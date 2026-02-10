package com.lwd.admin.config.flex;

import com.lwd.admin.constant.DbColumn;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.dialect.DbType;
import com.mybatisflex.core.dialect.DialectFactory;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.logicdelete.impl.IntegerLogicDeleteProcessor;
import com.mybatisflex.core.mybatis.FlexConfiguration;
import com.mybatisflex.core.table.DynamicSchemaProcessor;
import com.mybatisflex.spring.boot.ConfigurationCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Flex 配置.
 *
 * @author lwd
 */
@Slf4j
@Configuration
public class FlexConfig implements ConfigurationCustomizer {

    @Override
    public void customize(FlexConfiguration flexConfiguration) {
        FlexGlobalConfig globalConfig = FlexGlobalConfig.getDefaultConfig();
        // 主键生成配置 bigint
        FlexGlobalConfig.KeyConfig keyConfig = new FlexGlobalConfig.KeyConfig();
        keyConfig.setKeyType(KeyType.Generator);
        keyConfig.setValue(KeyGenerators.flexId);
        keyConfig.setBefore(true);
        globalConfig.setKeyConfig(keyConfig);
        // 全局乐观锁字段
        globalConfig.setVersionColumn(DbColumn.VERSION);
        // 逻辑删除处理器 0:正常,1:删除
        LogicDeleteManager.setProcessor(new IntegerLogicDeleteProcessor());
        // 全局逻辑删除字段
        globalConfig.setLogicDeleteColumn(DbColumn.IS_DELETED);
        // 全局监听器
        GlobalInsertListener globalInsertListener = new GlobalInsertListener();
        GlobalUpdateListener globalUpdateListener = new GlobalUpdateListener();
        globalConfig.registerInsertListener(globalInsertListener, Object.class);
        globalConfig.registerUpdateListener(globalUpdateListener, Object.class);
        globalConfig.registerSetListener(new GlobalOnSetListener(), Object.class);

        globalConfig.setDbType(DbType.POSTGRE_SQL);

        // 注册查询数据权限监听方言
        DialectFactory.registerDialect(DbType.POSTGRE_SQL, new DataAuthDialectImpl());
        // 开启审计功能
        AuditManager.setAuditEnable(true);
        // 设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage -> log.info("sql:{},exec:{}ms", auditMessage.getFullSql(),
                auditMessage.getElapsedTime()));
    }

    /**
     * 动态Schema
     */
    @Bean
    public DynamicSchemaProcessor dynamicSchemaProcessor() {
        return new MyDynamicSchemaProcessor();
    }
}
