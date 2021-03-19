package com.shopstick.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="cart")
@NamedQuery(name="Cart.findAll", query="SELECT c FROM Cart c")
@Data
public class Cart {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
	private Transaction transaction;
	
    @Column(name = "item_id")
	private Integer itemId;
    
//	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<Item> items;
	private Integer quantity;
}
