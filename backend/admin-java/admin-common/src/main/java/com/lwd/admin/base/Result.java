package com.lwd.admin.base;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * controller返回结果
 *
 * @author lwd
 */
@Data
public final class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 3318282101851595976L;

    private Boolean success;
    private Integer code;
    private String message;
    @SuppressWarnings("java:S1948")
    private T data;
    private LocalDateTime time;

    private Result() {}

    /**
     * 带数据的成功返回
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        result.setTime(LocalDateTime.now());
        return result;
    }

    /**
     * 无数据的成功返回
     */
    public static Result<Void> ok() {
        return ok(null);
    }

    /**
     * 自定义提示语的成功返回
     */
    public static <T> Result<T> ok(String message, T data) {
        Result<T> result = ok(data);
        result.setMessage(message);
        return result;
    }

    /**
     * 自定义状态码+提示语的成功返回
     */
    public static <T> Result<T> ok(Integer code, String message, T data) {
        Result<T> result = ok(data);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 自定义状态码+提示语的失败返回
     */
    public static Result<Void> fail(Integer code, String message) {
        Result<Void> result = new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        result.setData(null);
        result.setTime(LocalDateTime.now());
        return result;
    }

    /**
     * 通过枚举的失败返回
     */
    public static Result<Void> fail(ResultCode resultCode) {
        return fail(resultCode.getCode(), resultCode.getMessage());
    }

    /**
     * 默认的失败返回
     */
    public static Result<Void> fail() {
        return fail(ResultCode.SERVER_ERROR.getCode(), ResultCode.SERVER_ERROR.getMessage());
    }

    /**
     * 自定义提示语的失败返回
     */
    public static Result<Void> fail(String message) {
        return fail(ResultCode.SERVER_ERROR.getCode(), message);
    }
}
