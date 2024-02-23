package com.markdoyle.marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_seq")
	@SequenceGenerator(name = "car_seq", sequenceName = "car_seq", allocationSize = 1)
	private Integer id;

	@Column(length = 128, nullable = false)
	public String name;

	@Column(length = 128, nullable = false)
	public String price;

	@Column(length = 128, nullable = true)
	public String dlc;

	@Column(length = 128, nullable = false)
	public String img;

	@Column()
	public Integer capacity;

	public Car(String name) {
		this.name = name;
	}

	public Car() {}

	@Override
	public String toString() {
		return this.getName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
