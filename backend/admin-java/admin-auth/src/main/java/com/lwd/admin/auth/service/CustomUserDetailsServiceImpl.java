package com.lwd.admin.auth.service;

import com.lwd.admin.core.system.entity.User;
import com.lwd.admin.core.system.service.UserService;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lwd.admin.core.system.entity.table.Tables.USER;

/**
 * 自定义用户详情服务实现
 *
 * @author lwd
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Cacheable(value = "user", key = "#username", unless = "#result == null")
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.getOne(QueryWrapper.create()
                                                   .where(USER.USERNAME.eq(username)));
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        Set<String> permissions = Set.of("admin", "user");
        List<GrantedAuthority> authorities = permissions
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList())
                ;
        LocalDateTime now = LocalDateTime.now();
        boolean isAccountExpired = user.getAccountExpireTime() != null && user.getAccountExpireTime()
                                                                              .isBefore(now);
        boolean isCredentialsExpired = user.getCredentialsExpireTime() != null && user.getCredentialsExpireTime()
                                                                                      .isBefore(now);
        Integer userStatus = user.getUserStatus();
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(isAccountExpired)
                .accountLocked(userStatus == 2)
                .credentialsExpired(isCredentialsExpired)
                .disabled(userStatus == 1)
                .build();
    }
}
