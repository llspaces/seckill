package com.llspace.seckill.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <p>@filename SeckillOrder</p>
 * <p>
 * <p>@description 秒杀订单实体</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/27 18:03
 **/
@Data
public class SeckillOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long orderId;
    private Long goodsId;
}
