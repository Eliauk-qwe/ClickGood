package com.wly.clickgood.service;

import java.util.List;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wly.clickgood.model.entity.Blog;
import com.wly.clickgood.model.vo.BlogVo;

import jakarta.servlet.http.HttpServletRequest;

/**
* @author wly
* @description 针对表【blog】的数据库操作Service
* @createDate 2026-05-08 19:17:05
*/
public interface BlogService extends IService<Blog> {
    BlogVo getBlogVoById(long blogId,HttpServletRequest request);
    List<BlogVo>  getBlogVoList(List<Blog> bloglist,HttpServletRequest request);


}
