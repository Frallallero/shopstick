package com.shopstickcore.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="item")
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
@Data
public class Item {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String description;
	
	@Column(name="item_type", length=250)
	private String itemType;
	
	@Column(name="stock_number")
	private Integer stockNumber;
	private BigDecimal price;
	private String image;
}
