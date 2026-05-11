package com.wly.clickgood.controller;

import com.wly.clickgood.common.BaseResponse;
import com.wly.clickgood.common.ResultUtils;
import com.wly.clickgood.constant.UserConstant;
import com.wly.clickgood.model.entity.User;
import com.wly.clickgood.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController//方法返回值不要去找页面，而是直接写入 HTTP 响应体
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/login")
    public BaseResponse<User> login(@RequestParam("userid") Long userId, HttpServletRequest request) {
        User user = userService.getById(userId);
        
        request.getSession().setAttribute(UserConstant.LOGIN_USER, user);
        /*
        浏览器第一次请求
            ↓
        服务器创建 Session，并生成 JSESSIONID
            ↓
        浏览器保存 JSESSIONID 到 Cookie
            ↓
        登录成功后，服务器把 User 存到这个 Session
            ↓
        之后浏览器请求自动带 JSESSIONID
            ↓
        服务器根据 JSESSIONID 找到 Session
            ↓
        从 Session 里拿到 User

        */
        return ResultUtils.success(user);
    }


    @GetMapping("get/login")
    public BaseResponse<User> getLoginUser(HttpServletRequest request){
        User loginuser=(User) request.getSession().getAttribute(UserConstant.LOGIN_USER);


        return ResultUtils.success(loginuser);
    }
}
