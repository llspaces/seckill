package com.llspace.seckill.service;

import com.llspace.seckill.dao.SeckillOrderMapper;
import com.llspace.seckill.entity.SeckillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@filename SeckillOrderService</p>
 * <p>
 * <p>@description 秒杀订单模块service</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/7/2 11:51
 **/
@Service
public class SeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    public SeckillOrder findSeckillOrder(Long userId, long goodsId) {
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setGoodsId(goodsId);
        seckillOrder.setUserId(userId);
        return seckillOrderMapper.selectOne(seckillOrder);
    }

    public long insert(SeckillOrder seckillOrder) {
        return seckillOrderMapper.insert(seckillOrder);
    }
}
