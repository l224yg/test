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

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.GetGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(GoodsVo goods) {
        MiaoshaGoods g= new MiaoshaGoods();
        g.setGoodsId(goods.getId());
        int i = goodsDao.reduceStock(g);
        return i>0;
    }

    public void resetStock(List<GoodsVo> goodsList) {
        for(GoodsVo goods : goodsList ) {
            MiaoshaGoods g = new MiaoshaGoods();
            g.setGoodsId(goods.getId());
            g.setStockCount(goods.getStockCount());
            goodsDao.resetStock(g);
        }
    }

}
