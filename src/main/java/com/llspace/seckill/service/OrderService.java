package com.llspace.seckill.service;

import com.llspace.seckill.dao.OrderMapper;
import com.llspace.seckill.entity.OrderInfo;
import com.llspace.seckill.entity.SeckillOrder;
import com.llspace.seckill.entity.User;
import com.llspace.seckill.entity.vo.GoodsVO;
import com.llspace.seckill.utils.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>@filename OrderService</p>
 * <p>
 * <p>@description 订单模块service</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/7/2 14:20
 **/
@Service
@Slf4j
public class OrderService {

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private OrderMapper orderMapper;

    //创建订单，同时存入秒杀订单表
    @Transactional
    public OrderInfo createOrder(User user, GoodsVO goods) {
        OrderInfo order = new OrderInfo();
        order.setCreateDate(new Date());
        order.setDeliveryAddrId(0L);
        order.setGoodsCount(1);
        order.setGoodsId(goods.getId());
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsPrice(goods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(OrderStatus.CREATED.getStatusCode());
        order.setUserId(user.getId());
        log.info(order.toString());
        orderMapper.insert(order);

        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrder.setOrderId(order.getId());
        log.info(seckillOrder.toString());
        seckillOrderService.insert(seckillOrder);

        return order;
    }
}
