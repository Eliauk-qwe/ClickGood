package com.wly.clickgood.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wly.clickgood.common.BaseResponse;
import com.wly.clickgood.common.ResultUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理器。
 *
 * <p>Controller 中抛出的异常会被这里统一捕获，并转换成 BaseResponse 返回给前端。</p>
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常，保留业务异常中的错误码和错误信息。
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> BusinessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    /**
     * 捕获其他运行时异常，避免把系统内部错误细节直接暴露给前端。
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> RuntimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统运行异常");
    }
}
