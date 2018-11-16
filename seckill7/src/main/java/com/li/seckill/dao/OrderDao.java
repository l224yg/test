package com.li.seckill.dao;

import com.li.seckill.domain.MiaoshaOrder;
import com.li.seckill.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * @Auther Liyg
 * @Date 2018/11/2
 */
@Mapper
public interface OrderDao {
    @Select("select * from miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param(value="userId") Long userId,@Param(value="goodsId") Long goodsId);

    @Insert("insert into order_info(user_id,goods_id,delivery_addr_id,goods_name,goods_count,goods_price,order_channel,status,create_date) " +
            "values(#{userId},#{goodsId},#{deliveryAddrId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate})")
    @SelectKey(keyColumn = "id",keyProperty = "id", resultType = long.class,before=false,statement = "select last_insert_id()")
    long insert(OrderInfo orderInfo);

    @Insert("insert into miaosha_order(user_id,order_id,goods_id) values(#{userId},#{orderId},#{goodsId})")
    void insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    @Select("select * from order_info where id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId")long orderId);

    @Delete("delete from order_info")
    void deleteOrders();

    @Delete("delete from miaosha_order")
    void deleteMiaoshaOrders();
}
