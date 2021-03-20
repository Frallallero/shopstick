package com.shopstick.core.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name="r_cart_item")
@NamedQuery(name="CartItem.findAll", query="SELECT c FROM CartItem c")
@Data
public class CartItem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "r_cart_item_sq")
    @SequenceGenerator(name = "r_cart_item_sq", sequenceName = "r_cart_item_sq", allocationSize = 1)
	private Integer id;

	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="cart_id")
	private Cart cart;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="item_id")
	private Item item;
	private Integer quantity;
	
}
