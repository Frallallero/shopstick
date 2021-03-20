package com.shopstick.core.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopstick.core.vo.ItemVO;

import lombok.Data;

@Entity
@Table(name="item")
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
@Data
public class Item {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String description;
	
	@Column(name="category", length=250)
	private String category;
	
	@Column(name="stock_number")
	private Integer stockNumber;
	private BigDecimal price;
	private String image;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "items", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Cart> carts;
	
	public Item() {}
	
	public Item(ItemVO item) {
		this.name = item.getName();
		this.description = item.getDescription();
		this.price = new BigDecimal(item.getPrice());
		this.category = item.getCategory();
		this.image = item.getImage();
		this.stockNumber = Integer.valueOf(item.getStockNumber());
	}
	
}
