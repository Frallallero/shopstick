package com.shopstick.core.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name="shop_user")
@NamedQuery(name="ShopUser.findAll", query="SELECT s FROM ShopUser s")
@Data
public class ShopUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5997443652190782695L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shop_user_sq")
    @SequenceGenerator(name = "shop_user_sq", sequenceName = "shop_user_sq", allocationSize = 1)
	private Integer id;
	
	@JsonManagedReference
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="role_id", nullable=false)
	private Role role;
	
	private String name;
	private String username;
	@JsonIgnore
	private String password;
	
	@JsonBackReference
	@OneToMany(mappedBy = "shopUser", cascade=CascadeType.ALL, orphanRemoval = true)
	private List<Transaction> transactions;
}