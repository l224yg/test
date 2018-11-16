package com.li.seckill.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Goods {
	private Long id;
	private String goodsName;
	private String goodsTitle;
	private String goodsImg;
	private String goodsDetail;
	private Double goodsPrice;
	private Integer goodsStock;

}
