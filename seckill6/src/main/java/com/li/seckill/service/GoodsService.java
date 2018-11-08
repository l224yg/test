package com.li.seckill.service;

import com.li.seckill.dao.GoodsDao;
import com.li.seckill.domain.Goods;
import com.li.seckill.domain.MiaoshaGoods;
import com.li.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther Liyg
 * @Date 2018/11/1
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.getGoodsVo();
    }

    public GoodsVo GetGoodsVoByGoodsId(long goodsId) {
        return goodsDao.GetGoodsVoByGoodsId(goodsId);
    }

    public void reduceStock(GoodsVo goods) {
        MiaoshaGoods g= new MiaoshaGoods();
        g.setGoodsId(goods.getId());
        goodsDao.reduceStock(g);
    }
}
