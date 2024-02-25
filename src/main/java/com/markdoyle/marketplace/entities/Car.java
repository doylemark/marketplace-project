package com.markdoyle.marketplace.entities;

import jakarta.persistence.*;

@Entity
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_seq")
	@SequenceGenerator(name = "car_seq", sequenceName = "car_seq", allocationSize = 1)
	private Integer id;

	@Column(length = 128, nullable = false, unique = true)
	public String name;

	@Column(nullable = false)
	public Integer price;

	@Column(length = 128, nullable = true)
	public String dlc;

	@Column(length = 128, nullable = true)
	public String img;

	@Column(nullable = false)
	public Integer capacity;

	@Column(length = 512, nullable = false)
	public String description;

	@Column(nullable = false, columnDefinition = "boolean default false")
	public boolean isDeleted;

	public Car() {
	}

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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getDlc() {
		return dlc;
	}

	public void setDlc(String dlc) {
		this.dlc = dlc;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
