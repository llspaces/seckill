package com.llspace.seckill.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>@filename User</p>
 * <p>
 * <p>@description 用户实体</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/19 15:56
 **/
@Data
@Table(name = "seckill_user")
public class User {
    @Id
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}
