package com.shopstick.core.vo;

import com.shopstick.core.entity.CartItem;
import com.shopstick.core.entity.Item;

import lombok.Data;

@Data
public class UserItemVO {
	private Item item;
	private Integer quantity;
	
	public UserItemVO(CartItem cartItem) {
		this.item = cartItem.getItem();
		this.quantity = cartItem.getQuantity();
	}
}
