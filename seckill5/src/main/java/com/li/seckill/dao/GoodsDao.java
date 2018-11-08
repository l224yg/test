package com.li.seckill.dao;

import com.li.seckill.domain.Goods;
import com.li.seckill.domain.MiaoshaGoods;
import com.li.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Auther Liyg
 * @Date 2018/11/1
 */
@Mapper
public interface GoodsDao {

    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id=g.id")
    List<GoodsVo> getGoodsVo();

    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id=g.id where goods_id=#{goodsId}")
    GoodsVo GetGoodsVoByGoodsId(long goodsId);

    @Update("update miaosha_goods set stock_count=stock_count-1 where goods_id=#{goodsId}")
    void reduceStock(MiaoshaGoods g);
}
