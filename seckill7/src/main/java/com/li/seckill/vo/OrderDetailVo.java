package com.li.seckill.vo;

import com.li.seckill.domain.OrderInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * @Auther Liyg
 * @Date 2018/11/15
 */
@Getter
@Setter
public class OrderDetailVo {

    private GoodsVo goods;
    private OrderInfo order;
}
