package com.li.seckill.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class MiaoshaGoods {
	private Long id;
	private Long goodsId;
	private Integer stockCount;
	private Date startDate;
	private Date endDate;

}
