package com.lwd.admin.core.component;

import com.lwd.admin.core.system.entity.Tenant;
import com.lwd.admin.core.system.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

/**
 * FlywayMigrator
 *
 * @author lwd
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FlywayMigrator {

    private final TenantService tenantService;
    private final FlywayProperties flywayProperties;
    private final DataSource dataSource;

    /**
     * 在应用程序启动时执行所有schema的迁移
     */
    @EventListener(ApplicationReadyEvent.class)
    public void migrateAllSchemasOnStartup() {
        log.info("开始同步所有schema...");
        try {
            migrateAllTenantSchemas();
            log.info("所有schema同步完成");
        } catch (Exception e) {
            log.error("同步schema时发生错误", e);
        }
    }

    /**
     * 迁移所有租户schema
     */
    private void migrateAllTenantSchemas() {
        log.info("开始迁移所有租户schema");
        // 获取所有租户
        List<Tenant> tenantList = tenantService.list();
        log.info("找到 {} 个租户，开始迁移", tenantList.size());
        for (Tenant tenant : tenantList) {
            try {
                migrateTenantSchema(tenant.getTenantCode());
                log.info("租户 {} schema迁移完成", tenant.getTenantName());
            } catch (Exception e) {
                log.error("租户 {} schema迁移失败", tenant.getTenantName(), e);
            }
        }
    }

    /**
     * 迁移指定租户的schema
     */
    public void migrateTenantSchema(String tenantSchema) {
        log.debug("开始迁移租户schema: {}", tenantSchema);
        Flyway flyway = Flyway.configure()
                              .dataSource(dataSource)
                              .schemas(tenantSchema)
                              .locations(flywayProperties.getLocations().toArray(new String[0]))
                              .baselineOnMigrate(true)
                              .load();
        flyway.migrate();
        log.debug("租户 {} schema迁移完成", tenantSchema);
    }
}
