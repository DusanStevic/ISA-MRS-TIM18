package com.tim18.skynet.dto;

import java.util.ArrayList;
import java.util.List;

public class RoomReservationDTO {
	private int days;
	private long roomId;
	private long reservationId;
	private List<Long> hotelOffers = new ArrayList<Long>();

	

	public RoomReservationDTO() {
		super();
	}

	public List<Long> getHotelOffers() {
		return hotelOffers;
	}



	public void setHotelOffers(List<Long> hotelOffers) {
		this.hotelOffers = hotelOffers;
	}



	public RoomReservationDTO(int days, long roomId, long reservationId, List<Long> hotelOffers) {
		super();
		this.days = days;
		this.roomId = roomId;
		this.reservationId = reservationId;
		this.hotelOffers = hotelOffers;
	}



	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public long getReservationId() {
		return reservationId;
	}

	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}
	
	
}

