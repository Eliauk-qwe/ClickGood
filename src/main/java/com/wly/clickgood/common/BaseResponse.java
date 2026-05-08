package com.wly.clickgood.common;

import java.io.Serializable;

import com.wly.clickgood.exception.ErrorCode;
import lombok.Data;

/**
 * 通用接口响应对象。
 *
 * <p>所有接口都统一返回 code、data、message，前端处理成功和失败结果时会更稳定。</p>
 *
 * @param <T> data 字段的数据类型，比如 String、User、List<User>
 */
@Data   // 用一行注解代替大量重复的 getter/setter 等常用方法。
public class BaseResponse<T> implements Serializable {

    /**
     * 业务状态码，0 通常表示成功，非 0 表示失败。
     */
    private int code;

    /**
     * 响应提示信息，比如 ok、请求参数错误。
     */
    private String message;

    /**
     * 真正返回给前端的数据。
     */
    private T data;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 没有额外提示信息时使用这个构造方法。
     */
    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    /**
     * 根据统一错误码直接构造失败响应。
     */
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
