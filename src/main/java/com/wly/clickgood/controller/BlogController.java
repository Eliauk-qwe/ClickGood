package com.wly.clickgood.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wly.clickgood.common.BaseResponse;
import com.wly.clickgood.common.ResultUtils;
import com.wly.clickgood.model.entity.Blog;
import com.wly.clickgood.model.vo.BlogVo;
import com.wly.clickgood.service.BlogService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Resource
    BlogService blogService;

    @GetMapping("/get")
    public BaseResponse<BlogVo> getBlog(@RequestParam("blogId") long blogId,HttpServletRequest request) {
        BlogVo blogvVo = blogService.getBlogVoById(blogId,request);
        return ResultUtils.success(blogvVo);

    }

    @GetMapping("list")
    public BaseResponse<List<BlogVo>> getBlogList(HttpServletRequest request){

        List<Blog> bloglist = blogService.list();
        List<BlogVo> blogVolist =blogService.getBlogVoList(bloglist, request);
        return ResultUtils.success(blogVolist);
        
    }
    
}
