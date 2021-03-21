package com.shopstick.core.vo;

import com.shopstick.core.entity.CartItem;
import com.shopstick.core.entity.Item;

import lombok.Data;

@Data
public class UserItemVO {
	private Integer cartId;
	private Item item;
	private Integer quantity;
	
	public UserItemVO(CartItem cartItem) {
		this.cartId = cartItem.getCart().getId();
		this.item = cartItem.getItem();
		this.quantity = cartItem.getQuantity();
	}
}
