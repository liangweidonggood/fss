package com.lwd.admin.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.lwd.admin.entity.User;
import com.lwd.admin.mapper.UserMapper;
import com.lwd.admin.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表 服务层实现。
 *
 * @author lwd
 * @since 2026-01-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService{

}
