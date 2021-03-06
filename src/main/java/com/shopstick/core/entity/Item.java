package com.shopstick.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopstick.core.vo.ItemVO;

import lombok.Data;

@Entity
@Table(name="item")
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
@Data
public class Item implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6492754356910862451L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_sq")
    @SequenceGenerator(name = "item_sq", sequenceName = "item_sq", allocationSize = 1)
	private Integer id;
	private String name;
	private String description;
	
	@Column(name="category", length=250)
	private String category;
	
	@Column(name="stock_number")
	private Integer stockNumber;
	private BigDecimal price;
	private String image;
	
	@JsonBackReference
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CartItem> cartItems;
	
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
