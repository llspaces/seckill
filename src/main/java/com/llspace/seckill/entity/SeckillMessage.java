package com.llspace.seckill.entity;

import lombok.Data;

/**
 * <p>@filename SeckillMessage</p>
 * <p>
 * <p>@description 秒杀系统消息实体类</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/7/16 12:00
 **/
@Data
public class SeckillMessage {

    private User user;

    private long goodsId;
}
