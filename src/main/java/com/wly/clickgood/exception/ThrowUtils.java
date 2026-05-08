package com.wly.clickgood.exception;

/**
 * 抛异常工具类。
 *
 * <p>用于把常见的 if 判断抛异常写法封装起来，让业务代码更简洁。</p>
 */
public class ThrowUtils {

    /**
     * 如果条件成立，就抛出传入的运行时异常。
     */
    public static void throwIf(boolean condition, RuntimeException e) {
        if (condition) {
            throw e;
        }
    }

    /**
     * 如果条件成立，就根据错误码抛出业务异常。
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        if (condition) {
            throwIf(condition, new BusinessException(errorCode));
        }
    }

    /**
     * 如果条件成立，就根据错误码和自定义信息抛出业务异常。
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        if (condition) {
            throwIf(condition, new BusinessException(errorCode, message));
        }
    }
}
