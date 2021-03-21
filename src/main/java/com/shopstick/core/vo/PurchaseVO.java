package com.shopstick.core.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PurchaseVO {
	private Integer cartId;
	private String cardName;
	private String cardSurname;
	private String cardNumber;
	private String cvv;
	private BigDecimal total;
}
