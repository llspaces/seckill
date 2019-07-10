package com.llspace.seckill.entity.vo;

import com.llspace.seckill.entity.User;
import lombok.Data;

/**
 * <p>@filename GoodsDetailVO</p>
 * <p>
 * <p>@description 商品详情页面静态化VO</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/7/10 10:58
 **/
@Data
public class GoodsDetailVO {

    private GoodsVO goodsVO;
    private User user;
    private int seckillStatus = 0;
    private int remainSeconds = 0;

}
