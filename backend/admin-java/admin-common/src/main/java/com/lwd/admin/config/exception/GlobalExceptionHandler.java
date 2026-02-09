package com.lwd.admin.config.exception;

import com.lwd.admin.base.Result;
import com.lwd.admin.base.ResultCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 全局异常处理
 *
 * @author lwd
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException be) {
        log.warn("业务异常: {}", be.getMessage());
        return Result.fail(be.getCode(), be.getMessage());
    }

    /**
     * 请求资源不存在
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public Result<Void> handleNoResourceFoundException(NoResourceFoundException ex) {
        log.warn("资源不存在: {}", ex.getMessage());
        return Result.fail(ResultCode.REQ_RESOURCE_ERROR);
    }

    /**
     * 请求方式错误
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Void> handleMethodNotSupported(Exception ex) {
        log.error("请求方式错误: ", ex);
        return Result.fail(ResultCode.REQ_METHOD_ERROR);
    }

    /**
     * 处理所有未捕获的异常
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleOtherExceptions(Exception ex) {
        log.error("系统异常: ", ex);
        return Result.fail(ResultCode.SERVER_ERROR);
    }

    /**
     * 处理参数校验异常（@Valid）
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result<Void> handleValidationExceptions(Exception ex) {
        return Result.fail(
                ResultCode.PARAM_ERROR.getCode(),
                (ex instanceof MethodArgumentNotValidException methodEx ?
                        methodEx.getBindingResult() : ((BindException) ex).getBindingResult())
                        .getAllErrors()
                        .stream()
                        .findFirst()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .orElse("参数错误"));
    }

    /**
     * RequestParam参数校验
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleConstraintViolationException(ConstraintViolationException ex) {
        return Result.fail(
                ResultCode.PARAM_ERROR.getCode(),
                ex.getConstraintViolations()
                  .stream()
                  .map(ConstraintViolation::getMessage)
                  .findFirst()
                  .orElse("参数校验失败"));
    }
}
