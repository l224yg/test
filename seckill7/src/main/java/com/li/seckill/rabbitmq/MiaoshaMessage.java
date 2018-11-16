package com.li.seckill.rabbitmq;

import com.li.seckill.domain.User;
import lombok.Getter;
import lombok.Setter;

/**
 * @Auther Liyg
 * @Date 2018/11/14
 */
@Getter
@Setter
public class MiaoshaMessage {
    private User user;
    private long goodsId;

}
