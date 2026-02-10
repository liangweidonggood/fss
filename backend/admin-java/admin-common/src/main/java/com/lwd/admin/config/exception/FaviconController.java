package com.lwd.admin.config.exception;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认 favicon.ico 请求处理
 *
 * @author lwd
 */
@Tag(name = "favicon", description = "favicon")
@RestController
public class FaviconController {

    /**
     * 默认 favicon.ico 请求处理
     */
    @Operation(summary = "favicon")
    @GetMapping("/favicon.ico")
    public void favicon(HttpServletResponse response) {
        // 返回 204 无内容，避免浏览器反复请求
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
