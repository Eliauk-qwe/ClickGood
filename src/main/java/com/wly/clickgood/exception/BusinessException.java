package com.wly.clickgood.exception;

import lombok.Data;

/**
 * 自定义业务异常。
 *
 * <p>业务异常表示“程序没坏，但当前请求不符合业务规则”，比如参数错误、未登录、无权限。</p>
 */
@Data
public class BusinessException extends RuntimeException {

    /**
     * 业务错误码，用于返回给前端做判断。
     */
    private final int code;

    /**
     * 直接传入错误码和错误信息。
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 使用统一错误码中的 code 和 message。
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    /**
     * 使用统一错误码中的 code，但自定义本次异常的 message。
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
}
