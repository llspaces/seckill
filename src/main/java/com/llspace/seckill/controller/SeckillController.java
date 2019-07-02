package com.llspace.seckill.controller;

import com.llspace.seckill.entity.OrderInfo;
import com.llspace.seckill.entity.SeckillOrder;
import com.llspace.seckill.entity.User;
import com.llspace.seckill.entity.vo.GoodsVO;
import com.llspace.seckill.service.GoodsService;
import com.llspace.seckill.service.SeckillOrderService;
import com.llspace.seckill.service.SeckillService;
import com.llspace.seckill.utils.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>@filename SeckillController</p>
 * <p>
 * <p>@description 秒杀功能Controller</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/7/2 11:37
 **/
@RequestMapping("/seckill")
@Controller
public class SeckillController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @RequestMapping("/do_seckill")
    public String seckill(Model model, User user, long goodsId){
        model.addAttribute("user", user);
        if(user == null){
            return "login";
        }
        //判断库存
        GoodsVO goods = goodsService.findGoodsVOByID(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0){
            //秒杀失败
            model.addAttribute("errmsg", CodeMsg.SECKILL_OVER.getMsg());
            return "seckill_fail";
        }
        //判断是否已经秒杀到
        SeckillOrder seckillOrder = seckillOrderService.findSeckillOrder(user.getId(), goodsId);
        if(seckillOrder !=null){
            //重复秒杀
            model.addAttribute("errmsg", CodeMsg.REPEATE_SECKILL.getMsg());
            return "seckill_fail";
        }
        //减库存，下订单，存入秒杀订单
        OrderInfo order = seckillService.seckill(user, goods);
        model.addAttribute("orderInfo", order);
        model.addAttribute("goods", goods);
        return "order_detail";
    }
}
