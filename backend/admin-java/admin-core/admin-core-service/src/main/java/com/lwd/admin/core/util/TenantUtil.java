package com.lwd.admin.core.util;

import com.lwd.admin.config.CustomConfigHolder;
import com.lwd.admin.config.CustomConfigProperties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 租户工具类
 *
 * @author lwd
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TenantUtil {

    public static CustomConfigProperties.MultiTenant getMultiTenantConfig() {
        return CustomConfigHolder.getConfig().multiTenant();
    }
}
