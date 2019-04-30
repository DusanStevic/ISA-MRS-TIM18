package com.tim18.skynet.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.tim18.skynet.dto.RoomDTO;

@Entity
public class Room {
	@Id
	@GeneratedValue
	private long id;
	@Column(nullable = false)
	private double price;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private int bedNumber;
	@Column(nullable = false)
	private String image;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Hotel hotel;

	public Room(double price, String description, int bedNumber, String image, Hotel hotel) {
		super();
		this.price = price;
		this.description = description;
		this.bedNumber = bedNumber;
		this.image = image;
		this.hotel = hotel;
	}

	public Room(RoomDTO room) {
		super();
		this.image = room.getImage();
		this.bedNumber = room.getBeds();
		this.price = room.getPrice();
		this.description = room.getDescription();
	}
	
	public Room() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getBedNumber() {
		return bedNumber;
	}

	public void setBedNumber(int bedNumber) {
		this.bedNumber = bedNumber;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
}
