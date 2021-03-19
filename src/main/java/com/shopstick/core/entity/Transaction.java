package com.shopstick.core.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="transaction")
@NamedQuery(name="Transaction.findAll", query="SELECT t FROM Transaction t")
@Data
public class Transaction {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_sq")
    @SequenceGenerator(name = "transaction_sq", sequenceName = "transaction_sq", allocationSize = 1)
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="shop_user_id")
	private ShopUser shopUser;
	
	@Column(name="status_id")
	private Integer statusId;
	
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "transaction")
	private Cart cart;
}
