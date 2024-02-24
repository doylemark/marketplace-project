package com.markdoyle.marketplace.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class CartItem {
	public CartItem() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_item_seq")
	@SequenceGenerator(name = "cart_item_seq", sequenceName = "cart_item_seq", allocationSize = 1)
	public Integer id;

	@Column(nullable = false)
	public Integer quantity;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	public User user;

	@ManyToOne
	@JoinColumn(name = "car_id")
	public Car car;
}
