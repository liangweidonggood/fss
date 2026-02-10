package com.lwd.admin.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.function.Supplier;

/**
 * 动态权限管理器
 *
 * @author lwd
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        String uri = object.getRequest()
                           .getRequestURI();
        log.info("请求 {}", uri);
        Authentication auth = authentication.get();
        // 未登录或者匿名用户直接拒绝
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return new AuthorizationDecision(false);
        }
        String reqPermission = getPermission(uri);
        // 没配权限标识拒绝
        if (!StringUtils.hasText(reqPermission)) {
            log.info("请求 {} 无权限", uri);
            return new AuthorizationDecision(false);
        }
        // 匹配权限
        return new AuthorizationDecision(
                auth.getAuthorities()
                    .stream()
                    .anyMatch(authority -> authority.getAuthority()
                                    .equals(reqPermission))
        );
    }

    private String getPermission(String uri) {
        String defaultPermission = "abc";
        if (defaultPermission.equals(uri)) {
            return null;
        }
        return "admin";
    }
}
