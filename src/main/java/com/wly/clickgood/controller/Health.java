package com.wly.clickgood.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wly.clickgood.common.BaseResponse;
import com.wly.clickgood.common.ResultUtils;

/**
 * 健康检查接口。
 *
 * <p>用于确认后端服务是否正常启动。</p>
 */
@RestController
@RequestMapping("/")
public class Health {

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public BaseResponse<String> health() {
        return ResultUtils.success("ok");
    }
}
