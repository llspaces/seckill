package com.llspace.seckill.controller;

import com.llspace.seckill.entity.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>@filename GoodController</p>
 * <p>
 * <p>@description 商品Controller层</p>
 *
 * @author liyupeng
 * @version 1.0
 * @since 2019/6/21 16:45
 **/
@RestController
@RequestMapping("/good")
public class GoodController {

    @RequestMapping("/list")
    public String list(HttpServletRequest request, HttpServletResponse response, Model model,User user){
        model.addAttribute("user", user);
        return user.getNickname();
    }

}
