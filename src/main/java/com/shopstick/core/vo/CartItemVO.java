package com.shopstick.core.vo;

import lombok.Data;

@Data
public class CartItemVO {
	private Integer cartId;
	private Integer userId;
	private Integer itemId;
	private Integer quantity;
}
