package com.llspace.seckill.dao;

import com.llspace.seckill.entity.Goods;
import com.llspace.seckill.entity.SeckillGoods;
import com.llspace.seckill.entity.vo.GoodsVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>@filename GoodsMapper</p>
 * <p>
 * <p>@description 使用通用mapper实现秒杀商品dao层</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/27 18:04
 **/
public interface SeckillGoodsMapper extends Mapper<SeckillGoods> {

    @Update("update seckill_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    public int reduceStock(@Param("goodsId") long goodsId);

}
