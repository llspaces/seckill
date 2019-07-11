package com.llspace.seckill.service;

import com.llspace.seckill.dao.SeckillGoodsMapper;
import com.llspace.seckill.entity.SeckillGoods;
import com.llspace.seckill.entity.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
public class SeckillGoodsService {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    public boolean reduceStock(long goodsId) {
        int ret = seckillGoodsMapper.reduceStock(goodsId);
        return ret > 0;
    }
}
