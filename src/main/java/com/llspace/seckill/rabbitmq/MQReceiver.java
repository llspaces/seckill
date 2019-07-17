package com.llspace.seckill.rabbitmq;

import com.llspace.seckill.entity.SeckillMessage;
import com.llspace.seckill.entity.SeckillOrder;
import com.llspace.seckill.entity.User;
import com.llspace.seckill.entity.vo.GoodsVO;
import com.llspace.seckill.redis.RedisService;
import com.llspace.seckill.service.GoodsService;
import com.llspace.seckill.service.SeckillOrderService;
import com.llspace.seckill.service.SeckillService;
import com.llspace.seckill.utils.JsonUtil;
import com.llspace.seckill.utils.SeckillConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * <p>@filename MQReceiver</p>
 * <p>
 * <p>@description 消息接收者</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/7/12 14:46
 **/
@Component
@Slf4j
public class MQReceiver {

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private SeckillService seckillService;

    @RabbitListener(queues = SeckillConstant.QUEUE_NAME)
    public void receive(String message) {
        log.info("receive message:" + message);
        SeckillMessage seckillMessage = JsonUtil.toBean(message, SeckillMessage.class);
        User user = seckillMessage.getUser();
        long goodsId = seckillMessage.getGoodsId();

        GoodsVO goods = goodsService.findGoodsVOByID(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        SeckillOrder order = seckillOrderService.findSeckillOrder(user.getId(), goodsId);
        if (order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        seckillService.seckill(user, goods);
    }

}
