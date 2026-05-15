package com.wly.clickgood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.clickgood.model.entity.Blog;
import com.wly.clickgood.model.entity.Clickgood;
import com.wly.clickgood.model.entity.User;
import com.wly.clickgood.model.vo.BlogVo;
import com.wly.clickgood.exception.ErrorCode;
import com.wly.clickgood.exception.ThrowUtils;
import com.wly.clickgood.service.BlogService;
import com.wly.clickgood.service.ClickgoodService;
import com.wly.clickgood.service.UserService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import com.wly.clickgood.mapper.BlogMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
* @author wly
* @description 针对表【blog】的数据库操作Service实现
* @createDate 2026-05-08 19:17:05
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{
        @Resource
        private UserService userService;

        @Resource
        @Lazy
        private ClickgoodService clickgoodService;

        private BlogVo getBlogVo (Blog blog,User loginUser){
            BlogVo blogVo = new BlogVo();
            BeanUtil.copyProperties(blog,blogVo);

            if(loginUser == null){
                return blogVo;
            }

            Clickgood clickgood = clickgoodService.lambdaQuery()
            
                    .eq(Clickgood::getBlogId, blog.getId())
                    .eq(Clickgood::getUserId, loginUser.getId())
                    .one();
            blogVo.setHasClickGood(clickgood != null);

            return blogVo;

        }


        
        
        public BlogVo getBlogVoById(long blogId,HttpServletRequest request){
            ThrowUtils.throwIf(blogId <= 0, ErrorCode.PARAMS_ERROR, "博客 id 不合法");
            Blog blog = this.getById(blogId);
            ThrowUtils.throwIf(blog == null, ErrorCode.NOT_FOUND_ERROR, "博客不存在");
            User loginUser =userService.getLoginUser(request);
            return this.getBlogVo(blog, loginUser);

            
        }

        public  List<BlogVo>  getBlogVoList(List<Blog> bloglist,HttpServletRequest request){
            //获取谁的？
            User loginUser = userService.getLoginUser(request);

            Map<Long,Boolean> blogIdHasCGMap =new HashMap<>();

            if(ObjUtil.isNotEmpty(loginUser)){
                Set<Long> blogIdSet = bloglist.stream().map(Blog::getId).collect(Collectors.toSet());

                List<Clickgood> CGList =clickgoodService.lambdaQuery()
                            .eq(Clickgood::getUserId,loginUser.getId())
                            .in(Clickgood::getBlogId,blogIdSet)
                            .list();

                CGList.forEach(blogClickgood-> blogIdHasCGMap.put(blogClickgood.getBlogId(), true));

            }

            return bloglist.stream()
                .map(blog->{
                    BlogVo blogVo = BeanUtil.copyProperties(blog, BlogVo.class) ;
                    blogVo.setHasClickGood(blogIdHasCGMap.get(blog.getId()));
                    return blogVo;

                })
                .toList();
        }


}




