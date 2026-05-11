package com.wly.clickgood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wly.clickgood.model.entity.User;
import com.wly.clickgood.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import com.wly.clickgood.constant.UserConstant;
import com.wly.clickgood.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author wly
* @description 针对表【user】的数据库操作Service实现
* @createDate 2026-05-08 19:24:34
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
        
        public User getLoginUser (HttpServletRequest request){
            return (User) request.getSession().getAttribute(UserConstant.LOGIN_USER);
        }

}




