package com.lwd.admin.config.exception;

import com.lwd.admin.base.ResultCode;
import lombok.Getter;

import java.io.Serial;

/**
 * 业务异常
 *
 * @author lwd
 */
@Getter
public class BusinessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -330830066942054071L;

    private final int code;

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public BusinessException(ResultCode resultCode, Throwable cause) {
        super(resultCode.getMessage(), cause);
        this.code = resultCode.getCode();
    }

    public BusinessException(String msg) {
        super(msg);
        this.code = ResultCode.SERVER_ERROR.getCode();
    }

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
        this.code = ResultCode.SERVER_ERROR.getCode();
    }
}
