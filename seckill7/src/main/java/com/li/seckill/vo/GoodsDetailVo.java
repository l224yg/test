package com.li.seckill.vo;

import com.li.seckill.domain.User;
import lombok.Getter;
import lombok.Setter;

/**
 * @Auther Liyg
 * @Date 2018/11/15
 */
@Getter
@Setter
public class GoodsDetailVo {

    private int miaoshaStatus= 0;
    private int remainSeconds= 0;
    private GoodsVo goods;
    private User user;
}
