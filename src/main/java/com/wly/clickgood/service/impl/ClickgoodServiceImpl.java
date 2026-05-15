package com.wly.clickgood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.clickgood.exception.BusinessException;
import com.wly.clickgood.exception.ErrorCode;
import com.wly.clickgood.model.dto.clickgood.DoCGRequest;
import com.wly.clickgood.model.entity.Blog;
import com.wly.clickgood.model.entity.Clickgood;
import com.wly.clickgood.model.entity.User;
import com.wly.clickgood.service.BlogService;
import com.wly.clickgood.service.ClickgoodService;
import com.wly.clickgood.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.wly.clickgood.mapper.ClickgoodMapper;


import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
* @author wly
* @description 针对表【clickgood】的数据库操作Service实现
* @createDate 2026-05-08 19:22:50
*/
@Service
@RequiredArgsConstructor  // 自动生成构造函数
@Slf4j
public class ClickgoodServiceImpl extends ServiceImpl<ClickgoodMapper, Clickgood>   implements ClickgoodService{

    private final UserService userService;
    private final BlogService blogService;
    private final TransactionTemplate transactionTemplate;

    public Boolean doClickGood(DoCGRequest doCGRequest,HttpServletRequest httpServletRequest){
        if(doCGRequest == null || doCGRequest.getBlogId()==null)  {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }

        User loginUser = userService.getLoginUser(httpServletRequest);

        //加锁
        synchronized(loginUser.getId().toString().intern()){ 

        //编程式事物

        return transactionTemplate.execute(status -> {
            Long blogId = doCGRequest.getBlogId();
            boolean exists =this.lambdaQuery()
            .eq(Clickgood::getUserId,loginUser.getId())
            .eq(Clickgood::getBlogId,blogId)
            .exists();

            if(exists){
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户已点赞");

            }

            boolean update =blogService.lambdaUpdate()
                        .eq(Blog::getId,blogId)
                        .setSql("thumbCount=thumbCount+1")
                        .update();

            Clickgood clickgood = new Clickgood();
            clickgood.setUserId(loginUser.getId());
            clickgood.setBlogId(blogId);


            return update && this.save(clickgood);



        
        });
    }
        

    }



    public Boolean undoClickGood(DoCGRequest doCGRequest,HttpServletRequest httpServletRequest){
        if(doCGRequest == null || doCGRequest.getBlogId()==null)  {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }

        User loginUser = userService.getLoginUser(httpServletRequest);

        //加锁
        synchronized(loginUser.getId().toString().intern()){ 

        //编程式事物

        return transactionTemplate.execute(status -> {
            Long blogId = doCGRequest.getBlogId();
            Clickgood clickgood =this.lambdaQuery()
            .eq(Clickgood::getUserId,loginUser.getId())
            .eq(Clickgood::getBlogId,blogId)
            .one();

            if(clickgood == null){
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未点赞");

            }

            boolean update =blogService.lambdaUpdate()
                        .eq(Blog::getId,blogId)
                        .setSql("thumbCount=thumbCount-1")
                        .update();

            


            return update && this.removeById(clickgood.getId());



        
        });
    }
        

    }




}




