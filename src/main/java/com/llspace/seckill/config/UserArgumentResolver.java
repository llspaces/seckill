package com.llspace.seckill.config;

import com.llspace.seckill.entity.User;
import com.llspace.seckill.service.UserService;
import com.llspace.seckill.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * <p>@filename UserArgumentResolver</p>
 * <p>
 * <p>@description user参数处理</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/21 14:31
 **/
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver{

    @Autowired
    UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //拦截器中如果用户已登录则将用户信息放入UserContext，所以这里直接获取即可
        return UserContext.getUser();
    }
}
