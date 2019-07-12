package com.llspace.seckill.entity.vo;

import com.llspace.seckill.entity.OrderInfo;
import lombok.Data;

/**
 * <p>@filename SeckillOrderDetailVO</p>
 * <p>
 * <p>@description 秒杀订单详情页面静态化VO</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/7/11 16:12
 **/
@Data
public class SeckillOrderDetailVO {
    private GoodsVO goodsVO;
    private OrderInfo orderInfo;
}
