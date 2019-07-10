package com.llspace.seckill.controller;

import com.llspace.seckill.entity.User;
import com.llspace.seckill.entity.vo.GoodsDetailVO;
import com.llspace.seckill.entity.vo.GoodsVO;
import com.llspace.seckill.redis.GoodsRedisKeyPrefix;
import com.llspace.seckill.redis.RedisService;
import com.llspace.seckill.service.GoodsService;
import com.llspace.seckill.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>@filename GoodController</p>
 * <p>
 * <p>@description 商品Controller层</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/21 16:45
 **/
@Controller
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping("/list1")
    public String list1(HttpServletRequest request, HttpServletResponse response, Model model,User user){
        model.addAttribute("user", user);
        List<GoodsVO> goodsList = goodsService.listGoodsVO();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    /**
     * 商品列表优化（页面缓存）
     *
     * @param request
     * @param response
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model,User user){
        log.info("goods list.");
        //取缓存
        model.addAttribute("user", user);
        String html = redisService.getBean(GoodsRedisKeyPrefix.getGoodsList, "", String.class);
        if(!StringUtils.isEmpty(html)){
            //缓存不为空，直接返回
            return html;
        }
        //缓存为空
        List<GoodsVO> goodsList = goodsService.listGoodsVO();
        model.addAttribute("goodsList", goodsList);
        //spring4中是SpringWebContext,spring5如下
        IWebContext ctx =new WebContext(request,response,
            request.getServletContext(),request.getLocale(),model.asMap());
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if(!StringUtils.isEmpty(html)) {
            redisService.set(GoodsRedisKeyPrefix.getGoodsList, "", html);
        }
        return html;
    }

    /**
     * 商品详情优化（页面静态化）
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVO> detail(Model model,User user,@PathVariable("goodsId")long goodsId){
        log.info("goodsId : " + goodsId);
        GoodsVO goodsVO = goodsService.findGoodsVOByID(goodsId);
        long startAt = goodsVO.getStartDate().getTime();
        long endAt = goodsVO.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int seckillStatus = 0;
        int remainSeconds = 0;
        if(now < startAt ) {//秒杀还没开始，倒计时
            seckillStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            seckillStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        goodsDetailVO.setSeckillStatus(seckillStatus);
        goodsDetailVO.setRemainSeconds(remainSeconds);
        goodsDetailVO.setUser(user);
        goodsDetailVO.setGoodsVO(goodsVO);
        return Result.success(goodsDetailVO);
    }

    /**
     * 商品详情
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/detail1")
    public String detail1(Model model,User user,long goodsId){
        log.info("goodsId : " + goodsId);
        model.addAttribute("user", user);
        GoodsVO goodsVO = goodsService.findGoodsVOByID(goodsId);
        long startAt = goodsVO.getStartDate().getTime();
        long endAt = goodsVO.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int seckillStatus = 0;
        int remainSeconds = 0;
        if(now < startAt ) {//秒杀还没开始，倒计时
            seckillStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            seckillStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("goods", goodsVO);
        return "goods_detail";
    }

}
