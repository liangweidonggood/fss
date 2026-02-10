package com.lwd.admin.core.config.tenant;

import com.lwd.admin.config.SchemaContextHolder;
import com.lwd.admin.core.util.TenantUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 多租户拦截器
 *
 * @author lwd
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class TenantInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        log.debug("进入多租户拦截器");
        SchemaContextHolder.setCurrentSchema(TenantUtil.getMultiTenantConfig().defaultSchema());
        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) throws Exception {
        log.debug("退出多租户拦截器");
        SchemaContextHolder.clear();
    }
}
