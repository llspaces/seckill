package com.llspace.seckill.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * <p>@filename SeckillGoods</p>
 * <p>
 * <p>@description 秒杀商品实体</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/27 18:02
 **/
@Data
public class SeckillGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long goodsId;
    private Double seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
