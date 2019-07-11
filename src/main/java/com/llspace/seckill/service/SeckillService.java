package com.llspace.seckill.service;

import com.llspace.seckill.entity.Goods;
import com.llspace.seckill.entity.OrderInfo;
import com.llspace.seckill.entity.User;
import com.llspace.seckill.entity.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>@filename SeckillService</p>
 * <p>
 * <p>@description 秒杀service</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/7/2 12:07
 **/
@Service
public class SeckillService {

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Autowired
    private OrderService orderService;

    //减库存，下订单，存入秒杀订单
    @Transactional
    public OrderInfo seckill(User user, GoodsVO goods) {
        //减库存
        boolean success = seckillGoodsService.reduceStock(goods.getId());
        if(success){
            //下订单
            return orderService.createOrder(user, goods);
        }else {
            //秒杀结束
            return null;
        }



    }
}
