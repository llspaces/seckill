package com.llspace.seckill.entity.vo;

import com.llspace.seckill.entity.Goods;
import lombok.Data;

import java.util.Date;

/**
 * <p>@filename GoodsVO</p>
 * <p>
 * <p>@description 商品VO</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/27 18:09
 **/
@Data
public class GoodsVO extends Goods{
    private Integer stockCount;
    private Double seckillPrice;
    private Date startDate;
    private Date endDate;
}
