package com.lwd.admin.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 *
 * @author lwd
 */
@Tag(name = "认证", description = "认证")
@RestController
public class LoginController {

    /**
     * 登录
     */
    @PostMapping("/login")
    public String login() {
        return "登录成功";
    }
}
