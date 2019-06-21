package com.llspace.seckill.entity.vo;

import com.llspace.seckill.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * <p>@filename LoginVO</p>
 * <p>
 * <p>@description 登录VO</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/19 15:53
 **/
@Data
public class LoginVO {
    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min=32)
    private String password;

    @Override
    public String toString() {
        return "LoginVO [mobile=" + mobile + ", password=" + password + "]";
    }
}
