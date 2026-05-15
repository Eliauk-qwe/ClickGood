package com.wly.clickgood.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wly.clickgood.common.BaseResponse;
import com.wly.clickgood.common.ResultUtils;
import com.wly.clickgood.model.dto.clickgood.DoCGRequest;
import com.wly.clickgood.service.ClickgoodService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("cliclgood")
public class ClickGoodController {

    @Resource
    private ClickgoodService clickgoodService;

    @PostMapping("do")
    public BaseResponse<Boolean>  doClickGood(@RequestBody  DoCGRequest doCGRequest,HttpServletRequest request){
        Boolean success =clickgoodService.doClickGood(doCGRequest, request);
        return ResultUtils.success(success);
    }


    @PostMapping("undo")
    public BaseResponse<Boolean>  undoClickGood(@RequestBody  DoCGRequest doCGRequest,HttpServletRequest request){
        Boolean success =clickgoodService.undoClickGood(doCGRequest, request);
        return ResultUtils.success(success);
    }
    
}
