package com.lwd.admin.base;

import lombok.Getter;

/**
 *  响应结果状态码
 *
 * @author lwd
 */
@Getter
public enum ResultCode {
    // 操作成功
    SUCCESS(200, "操作成功"),
    UNAUTHORIZED(401, "无认证"),
    AUTH_FORBIDDEN(403, "无权限访问"),
    REQ_RESOURCE_ERROR(404, "请求资源不存在"),
    REQ_METHOD_ERROR(405, "请求方法不允许"),
    SERVER_ERROR(500, "服务异常"),
    PARAM_ERROR(10_400, "参数错误"),
    ;

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
