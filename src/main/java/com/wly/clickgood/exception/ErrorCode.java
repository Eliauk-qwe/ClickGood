package com.wly.clickgood.exception;

import lombok.Getter;

/**
 * 统一错误码。
 *
 * <p>把常见错误集中在这里管理，避免代码里到处写数字和错误文案。</p>
 */
@Getter
public enum ErrorCode {

    /**
     * 请求成功。
     */
    SUCCESS(0, "ok"),

    /**
     * 请求参数错误。
     */
    PARAMS_ERROR(40000, "请求参数错误"),

    /**
     * 用户未登录。
     */
    NOT_LOGIN_ERROR(40100, "未登录"),

    /**
     * 用户没有权限。
     */
    NO_AUTH_ERROR(40101, "无权限"),

    /**
     * 请求的数据不存在。
     */
    NOT_FOUND_ERROR(40400, "请求数据不存在"),

    /**
     * 禁止访问。
     */
    FORBIDDEN_ERROR(40300, "禁止访问"),

    /**
     * 系统内部异常。
     */
    SYSTEM_ERROR(50000, "系统内部异常"),

    /**
     * 操作失败。
     */
    OPERATION_ERROR(50001, "操作失败");

    /**
     * 错误码数字，返回给前端。
     */
    private final int code;

    /**
     * 错误提示信息。
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
