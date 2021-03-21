package com.shopstick.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name="cart")
@NamedQuery(name="Cart.findAll", query="SELECT c FROM Cart c")
@Data
public class Cart {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_sq")
    @SequenceGenerator(name = "cart_sq", sequenceName = "cart_sq", allocationSize = 1)
	private Integer id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
	private Transaction transaction;
	
	@JsonBackReference
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CartItem> cartItems;

	public List<CartItem> getCartItems() {
		return (cartItems==null) ? new ArrayList<>() : cartItems;
	}
	
}
