package com.shopstick.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
	private Transaction transaction;
	
	@ManyToMany
	@JoinTable(name = "r_cart_item", joinColumns=@JoinColumn(name = "cart_id"), inverseJoinColumns=@JoinColumn(name="item_id"))
	private Set<Item> items;

	public Set<Item> getItems() {
		if(items == null) {
			items = new HashSet<Item>();
		}
		return items;
	}
}
