package com.llspace.seckill.controller;

import com.llspace.seckill.entity.OrderInfo;
import com.llspace.seckill.entity.User;
import com.llspace.seckill.entity.vo.GoodsVO;
import com.llspace.seckill.entity.vo.SeckillOrderDetailVO;
import com.llspace.seckill.service.GoodsService;
import com.llspace.seckill.service.OrderService;
import com.llspace.seckill.utils.CodeMsg;
import com.llspace.seckill.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>@filename SeckillOrderController</p>
 * <p>
 * <p>@description 秒杀订单控制层</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/7/11 16:06
 **/
@RequestMapping("/order")
@Controller
public class SeckillOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<SeckillOrderDetailVO> detail(Model model,User user, @RequestParam("orderId") long orderId) {
        OrderInfo order = orderService.getOrderById(orderId);
        if(order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVO goods = goodsService.findGoodsVOByID(goodsId);
        SeckillOrderDetailVO vo = new SeckillOrderDetailVO();
        vo.setOrderInfo(order);
        vo.setGoodsVO(goods);
        return Result.success(vo);
    }


}
