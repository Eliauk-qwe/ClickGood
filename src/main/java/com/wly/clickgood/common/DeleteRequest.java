package com.wly.clickgood.common;

import java.io.Serializable;

import lombok.Data;

/**
 * 通用删除请求。
 *
 * <p>很多删除接口只需要传一个 id，因此抽成公共请求对象。</p>
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * 要删除的数据 id。
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
