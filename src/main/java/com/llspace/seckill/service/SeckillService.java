package com.llspace.seckill.service;

import com.llspace.seckill.entity.Goods;
import com.llspace.seckill.entity.OrderInfo;
import com.llspace.seckill.entity.SeckillOrder;
import com.llspace.seckill.entity.User;
import com.llspace.seckill.entity.vo.GoodsVO;
import com.llspace.seckill.redis.GoodsRedisKeyPrefix;
import com.llspace.seckill.redis.RedisService;
import com.llspace.seckill.redis.SeckillRedisKeyPrefix;
import com.llspace.seckill.utils.MD5Util;
import com.llspace.seckill.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

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

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private RedisService redisService;

    //减库存，下订单，存入秒杀订单
    @Transactional
    public OrderInfo seckill(User user, GoodsVO goods) {
        //减库存
        boolean success = seckillGoodsService.reduceStock(goods.getId());
        if(success){
            //下订单
            return orderService.createOrder(user, goods);
        }else {
            //秒杀结束，标记商品已秒杀完
            setGoodsSeckillOver(goods.getId());
            return null;
        }
    }

    private void setGoodsSeckillOver(Long goodsId) {
        redisService.set(GoodsRedisKeyPrefix.isGoodsSeckillOver, "" + goodsId, true);
    }

    public long getSeckillResult(long userId, long goodsId) {
        SeckillOrder seckillOrder = seckillOrderService.findSeckillOrder(userId, goodsId);
        if(seckillOrder == null){
            //获取商品是否已经秒杀完
            boolean isOver = getGoodsSeckillOver(goodsId);
            if(isOver){
                return -1;
            }else{
                return 0;
            }

        }else {
            //秒杀成功
            return seckillOrder.getOrderId();
        }
    }

    private boolean getGoodsSeckillOver(long goodsId) {
        return redisService.exists(GoodsRedisKeyPrefix.isGoodsSeckillOver, "" + goodsId);
    }

    public boolean checkPath(long userId, long goodsId, String path) {
        if(userId <= 0 || path == null || goodsId <=0) {
            return false;
        }
        String redisPath = redisService.getBean(SeckillRedisKeyPrefix.seckillPath, "_" + userId + "_" + goodsId, String.class);
        return path.equals(redisPath);
    }

    public String createPath(long userId, long goodsId) {
        if(userId <= 0 || goodsId <= 0) {
            return null;
        }
        String path = MD5Util.md5(UUIDUtil.uuid() + "-" + userId + "-" + goodsId);
        redisService.set(SeckillRedisKeyPrefix.seckillPath, "_" + userId + "_" + goodsId, path);
        return path;
    }

    public BufferedImage createVerifyCode(User user, long goodsId) {
        if(user == null || goodsId <= 0) {
            return null;
        }
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(SeckillRedisKeyPrefix.getSeckillVerifyCode, "_" + user.getId() + "_" + goodsId, rnd);
        //输出图片
        return image;
    }

    public boolean checkVerifyCode(User user, long goodsId, int verifyCode) {
        if(user == null || goodsId <= 0) {
            return false;
        }
        Integer codeOld = redisService.getBean(SeckillRedisKeyPrefix.getSeckillVerifyCode, "_" + user.getId() + "_" + goodsId, Integer.class);
        if(codeOld == null || codeOld - verifyCode != 0 ) {
            return false;
        }
        redisService.delete(SeckillRedisKeyPrefix.getSeckillVerifyCode, "_" + user.getId() + "_" + goodsId);
        return true;
    }

    /**
     * script engine
     *
     * @param exp
     * @return
     */
    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[] {'+', '-', '*'};
    /**
     * + - *
     * */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }

}
