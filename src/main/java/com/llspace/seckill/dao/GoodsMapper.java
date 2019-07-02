package com.llspace.seckill.dao;

import com.llspace.seckill.entity.Goods;
import com.llspace.seckill.entity.vo.GoodsVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>@filename GoodsMapper</p>
 * <p>
 * <p>@description 使用通用mapper实现商品dao层</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/27 18:04
 **/
public interface GoodsMapper extends Mapper<Goods> {

    @Select("select goods.*,secgoods.seckill_price,secgoods.stock_count,secgoods.start_date,secgoods.end_date from seckill_goods secgoods left join goods goods\n"
        + "on goods.id = secgoods.goods_id")
    public List<GoodsVO> listGoodsVO();

    @Select("select goods.*,secgoods.seckill_price,secgoods.stock_count,secgoods.start_date,secgoods.end_date from seckill_goods secgoods left join goods goods on goods.id = secgoods.goods_id where goods.id = #{goodsId}")
    public GoodsVO findGoodsVOByID(@Param("goodsId") long goodsId);
}
