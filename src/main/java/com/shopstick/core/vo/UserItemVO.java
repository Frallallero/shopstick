package com.shopstick.core.vo;

import java.math.BigDecimal;

import com.shopstick.core.entity.CartItem;
import com.shopstick.core.entity.Item;

import lombok.Data;

@Data
public class UserItemVO {
	private Integer cartId;
	private Item item;
	private Integer quantity;
	private BigDecimal totalAmount;
	
	public UserItemVO(CartItem cartItem) {
		this.cartId = cartItem.getCart().getId();
		this.item = cartItem.getItem();
		this.quantity = cartItem.getQuantity();
		this.totalAmount = calculateAmount();
	}
	
	private BigDecimal calculateAmount() {
		return item.getPrice().multiply(new BigDecimal(quantity));
	}
}
