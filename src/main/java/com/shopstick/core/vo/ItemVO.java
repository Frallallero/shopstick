package com.shopstick.core.vo;

import lombok.Data;

@Data
public class ItemVO {
	private String name;
	private String description;
	private String category;
	private String price;
	private String image;
	private String stockNumber;
}
