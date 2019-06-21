package com.llspace.seckill.dao;

import com.llspace.seckill.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * <p>@filename UserMapper</p>
 * <p>
 * <p>@description 使用通用mapper实现用户dao层</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/19 15:55
 **/
public interface UserMapper extends Mapper<User> {

}
