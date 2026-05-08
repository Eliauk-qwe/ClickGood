package com.wly.clickgood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.clickgood.model.entity.Blog;
import com.wly.clickgood.service.BlogService;

import com.wly.clickgood.mapper.BlogMapper;
import org.springframework.stereotype.Service;

/**
* @author wly
* @description 针对表【blog】的数据库操作Service实现
* @createDate 2026-05-08 19:17:05
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{

}




