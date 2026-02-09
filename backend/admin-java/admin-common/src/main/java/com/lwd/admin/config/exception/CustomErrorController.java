package com.lwd.admin.config.exception;

import com.lwd.admin.base.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * error controller
 *
 * @author lwd
 */
@Tag(name = "错误处理", description = "错误处理")
@RestController
public class CustomErrorController implements ErrorController {
    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    /**
     * 错误处理
     *
     * @param request 请求
     * @return 错误信息
     */
    @Operation(summary = "错误处理")
    @RequestMapping("/error")
    public Result<Void> handleError(HttpServletRequest request) {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        ErrorAttributeOptions options = ErrorAttributeOptions.defaults()
                                                             .including(ErrorAttributeOptions.Include.MESSAGE);
        Map<String, Object> error = errorAttributes.getErrorAttributes(webRequest, options);
        Object statusObj = error.get("status");
        int status = (statusObj instanceof Integer) ? (int) statusObj : 500;
        String message = error.getOrDefault("message", error.getOrDefault("error", "Unknown Error"))
                              .toString();
        return Result.fail(status, message);
    }
}
