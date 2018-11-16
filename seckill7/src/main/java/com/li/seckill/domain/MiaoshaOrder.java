package com.li.seckill.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MiaoshaOrder {
	private Long id;
	private Long userId;
	private Long  orderId;
	private Long goodsId;

}
