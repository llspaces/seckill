package com.llspace.seckill.controller;

import com.llspace.seckill.entity.OrderInfo;
import com.llspace.seckill.entity.SeckillMessage;
import com.llspace.seckill.entity.SeckillOrder;
import com.llspace.seckill.entity.User;
import com.llspace.seckill.entity.vo.GoodsVO;
import com.llspace.seckill.rabbitmq.MQSender;
import com.llspace.seckill.redis.GoodsRedisKeyPrefix;
import com.llspace.seckill.redis.RedisService;
import com.llspace.seckill.service.GoodsService;
import com.llspace.seckill.service.SeckillOrderService;
import com.llspace.seckill.service.SeckillService;
import com.llspace.seckill.utils.CodeMsg;
import com.llspace.seckill.utils.Result;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
public class SeckillController implements InitializingBean{

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private MQSender mqSender;

    @PostMapping("/do_seckill")
    @ResponseBody
    public Result<Integer> seckill(Model model, User user, long goodsId){
        //系统初始化时秒杀商品库存已初始化至redis
        //redis预减库存
        long stock = redisService.decr(GoodsRedisKeyPrefix.getSeckillGoodsStock, "" + goodsId);
        if(stock <= 0){
            //秒杀失败
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        //判断是否已经秒杀到
        SeckillOrder seckillOrder = seckillOrderService.findSeckillOrder(user.getId(), goodsId);
        if(seckillOrder !=null){
            //重复秒杀
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }
        //入队列rabbitmq
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setGoodsId(goodsId);
        seckillMessage.setUser(user);
        mqSender.sendSeckillMessage(seckillMessage);
        return Result.success(0);
    }

    /**
     * 获取秒杀结果
     *
     * @param model
     * @param user
     * @param goodsId
     * @return result（orderId 成功  -1 失败  0 排队中）
     */

    @GetMapping(value = "/result")
    @ResponseBody
    public Result<Long> seckillResult(Model model, User user, long goodsId){
        long result = seckillService.getSeckillResult(user.getId(), goodsId);
        return Result.success(result);
    }

    @PostMapping("/do_seckill2")
    @ResponseBody
    public Result<OrderInfo> seckill2(Model model, User user, long goodsId){
        //判断库存
        GoodsVO goods = goodsService.findGoodsVOByID(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0){
            //秒杀失败
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        //判断是否已经秒杀到
        SeckillOrder seckillOrder = seckillOrderService.findSeckillOrder(user.getId(), goodsId);
        if(seckillOrder !=null){
            //重复秒杀
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }
        //减库存，下订单，存入秒杀订单
        OrderInfo order = seckillService.seckill(user, goods);
        return Result.success(order);
    }

    @RequestMapping("/do_seckill1")
    public String seckill1(Model model, User user, long goodsId){
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

    /**
     * 实现InitializingBean接口，重写afterPropertiesSet()方法来实现
     * 系统初始化时，加载商品库存到Redis
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVO> goodsVOList = goodsService.listGoodsVO();
        if(goodsVOList == null) {
            return;
        }
        for(GoodsVO goodsVO : goodsVOList){
            redisService.set(GoodsRedisKeyPrefix.getSeckillGoodsStock, "" + goodsVO.getId(), goodsVO.getStockCount());
        }
    }
}
