package com.wly.clickgood.common;

import com.wly.clickgood.exception.ErrorCode;

/**
 * 返回结果工具类。
 *
 * <p>把创建 BaseResponse 的代码集中到这里，Controller 中只需要调用 success 或 error。</p>
 */
public class ResultUtils {

    /**
     * 构造成功响应。
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 根据错误码枚举构造失败响应。
     */
    public static BaseResponse<?> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 根据自定义错误码和错误信息构造失败响应。
     */
    public static BaseResponse<?> error(int code, String msg) {
        return new BaseResponse<>(code, null, msg);
    }

    /**
     * 使用错误码枚举中的 code，同时自定义本次返回的错误信息。
     */
    public static BaseResponse<?> error(ErrorCode errorCode, String msg) {
        return new BaseResponse<>(errorCode.getCode(), null, msg);
    }
}
