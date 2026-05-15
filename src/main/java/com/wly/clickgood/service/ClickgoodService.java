package com.wly.clickgood.service;

import com.wly.clickgood.model.dto.clickgood.DoCGRequest;
import com.wly.clickgood.model.entity.Clickgood;

import jakarta.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wly
* @description 针对表【clickgood】的数据库操作Service
* @createDate 2026-05-08 19:22:50
*/
public interface ClickgoodService extends IService<Clickgood> {
    Boolean doClickGood(DoCGRequest doCGRequest,HttpServletRequest httpServletRequest);
    Boolean undoClickGood(DoCGRequest doCGRequest,HttpServletRequest httpServletRequest);


}
