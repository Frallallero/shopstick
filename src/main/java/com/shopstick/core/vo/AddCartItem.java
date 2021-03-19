package com.shopstick.core.vo;

import lombok.Data;

@Data
public class AddCartItem {
	private Integer userId;
	private Integer itemId;
	private Integer quantity;
}
