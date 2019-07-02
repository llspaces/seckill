package com.llspace.seckill.service;

import com.llspace.seckill.dao.GoodsMapper;
import com.llspace.seckill.entity.Goods;
import com.llspace.seckill.entity.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>@filename GoodsService</p>
 * <p>
 * <p>@description 商品模块service层实现</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/27 18:08
 **/
@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public List<GoodsVO> listGoodsVO(){
        return goodsMapper.listGoodsVO();
    }

    public GoodsVO findGoodsVOByID(long goodsId) {
        return goodsMapper.findGoodsVOByID(goodsId);
    }

    public void reduceStock(Goods goods) {
        Goods g = new Goods();
        g.setGoodsStock(goods.getGoodsStock() - 1);
        g.setId(goods.getId());
        goodsMapper.updateByPrimaryKey(g);
    }
}
