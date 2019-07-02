package com.llspace.seckill.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <p>@filename Goods</p>
 * <p>
 * <p>@description 商品实体</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/27 18:01
 **/
@Data
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;
}
