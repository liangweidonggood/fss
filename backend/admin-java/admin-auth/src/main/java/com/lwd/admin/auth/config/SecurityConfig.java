package com.lwd.admin.auth.config;

import com.lwd.admin.config.CustomConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

/**
 * SpringSecurity配置类
 *
 * @author lwd
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver resolver;
    private final CustomConfigProperties properties;
    private final DynamicAuthorizationManager dynamicAuthorizationManager;
    private final AuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 配置过滤器链
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.authorizeHttpRequests(
                auth -> {
                    List<String> excludeUrls = properties.common().excludeUrls();
                    if (!CollectionUtils.isEmpty(excludeUrls)) {
                        auth.requestMatchers(excludeUrls.toArray(String[]::new)).permitAll();
                    }
                    auth.anyRequest().access(dynamicAuthorizationManager);
                });
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(exceptions -> exceptions
                // 未登录
                .authenticationEntryPoint((request, response, authException) ->
                        resolver.resolveException(request, response, null, authException)
                )
                // 权限不足
                .accessDeniedHandler((request, response, accessDeniedException) ->
                        resolver.resolveException(request, response, null, accessDeniedException)
                )
        );

        return http.build();
    }
}
