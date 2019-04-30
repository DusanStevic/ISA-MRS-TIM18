package com.tim18.skynet.dto;

public class RoomDTO {
	private String image;
	private double price;
	private int beds;
	private String description;
	private long roomId;
	private long hotelId;
	
	public RoomDTO(String image, double price, int beds, String description, long roomId, long hotelId) {
		super();
		this.image = image;
		this.price = price;
		this.beds = beds;
		this.description = description;
		this.roomId = roomId;
		this.hotelId = hotelId;
	}
	public RoomDTO() {
		super();
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getBeds() {
		return beds;
	}
	public void setBeds(int beds) {
		this.beds = beds;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getRoomId() {
		return roomId;
	}
	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}
	public long getHotelId() {
		return hotelId;
	}
	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}
}

