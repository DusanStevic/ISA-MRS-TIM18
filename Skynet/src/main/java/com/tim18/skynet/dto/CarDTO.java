package com.tim18.skynet.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.tim18.skynet.model.Branch;

public class CarDTO {

	private long id;
	private String name;
	private Double price;
	private Integer year;
	private String carType;
	private String brand;
	private String model;
	private Integer seats;
	private long branch;
	
	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public CarDTO() {
		super();
	}

	

	public CarDTO(long id, String name, Double price, Integer year, String carType, String brand, String model,
			Integer seats, long branch) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.year = year;
		this.carType = carType;
		this.brand = brand;
		this.model = model;
		this.seats = seats;
		this.branch = branch;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBranch() {
		return branch;
	}

	public void setBranch(long branch) {
		this.branch = branch;
	}
	
}
