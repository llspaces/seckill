package com.llspace.seckill.controller;

import com.llspace.seckill.entity.vo.LoginVO;
import com.llspace.seckill.utils.Result;
import com.llspace.seckill.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * <p>@filename LoginController</p>
 * <p>
 * <p>@description 系统登录Controller</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/19 15:47
 **/
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVO loginVO){
        log.info(loginVO.toString());
        //登录
        String token = userService.login(response, loginVO);
        return Result.success(token);
    }

    @RequestMapping("/do_logout")
    public String doLogout(@Length(min = 32) String token){
        userService.logout(token);
        return "login";
    }



}
