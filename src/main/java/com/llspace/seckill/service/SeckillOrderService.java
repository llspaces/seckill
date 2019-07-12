package com.llspace.seckill.service;

import com.llspace.seckill.dao.SeckillOrderMapper;
import com.llspace.seckill.entity.SeckillOrder;
import com.llspace.seckill.redis.RedisService;
import com.llspace.seckill.redis.SeckillOrderRedisKeyPrefix;
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

    @Autowired
    private RedisService redisService;

    public SeckillOrder findSeckillOrder(Long userId, long goodsId) {
        /*SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setGoodsId(goodsId);
        seckillOrder.setUserId(userId);
        return seckillOrderMapper.selectOne(seckillOrder);*/
        return redisService.getBean(SeckillOrderRedisKeyPrefix.getSeckillOrderByUidGid, "_" + userId + "_" + goodsId, SeckillOrder.class);
    }

    public long insert(SeckillOrder seckillOrder) {
        return seckillOrderMapper.insert(seckillOrder);
    }
}
