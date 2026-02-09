package com.lwd.admin.config.flyway;

import com.lwd.admin.system.entity.Tenant;
import com.lwd.admin.system.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

/**
 * Flyway租户同步服务
 * 用于在项目启动时同步所有租户的SQL语句
 *
 * @author lwd
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class FlywayTenantSyncService {

    private final DataSource dataSource;

    private final FlywayProperties flywayProperties;

    private final TenantService   tenantService;

    /**
     * 同步所有租户的SQL语句
     */
    public void syncAllTenants() {
        log.info("开始同步所有租户的SQL语句");
        try {
            // 获取所有租户信息
            List<Tenant> tenantList = tenantService.list();
            log.info("共找到 {} 个租户", tenantList.size());
            for (Tenant tenant : tenantList) {
                syncTenant(tenant.getTenantCode());
            }
            log.info("同步所有租户的SQL语句完成");
        } catch (Exception e) {
            log.error("同步租户SQL语句失败", e);
        }
    }


    /**
     * 同步单个租户的SQL语句
     */
    private void syncTenant(String tenantSchema) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas(tenantSchema)
                .locations(flywayProperties.getLocations().toArray(new String[0]))
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
    }
}
