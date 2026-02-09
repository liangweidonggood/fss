package com.lwd.admin.config.flyway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 应用启动监听器
 * 在应用启动完成后执行租户SQL同步
 *
 * @author lwd
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class FlywayTenantSyncListener implements ApplicationListener<ApplicationReadyEvent> {

    private final FlywayTenantSyncService flywayTenantSyncService;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        log.info("应用启动完成，开始执行租户SQL同步");
        flywayTenantSyncService.syncAllTenants();
    }
}
