package com.tim18.skynet.dto;

import java.util.ArrayList;
import java.util.List;


public class RoomOffersDTO {
	private List<String> roomOffers = new ArrayList<String>();

	public RoomOffersDTO(List<String> roomOffers) {
		super();
		this.roomOffers = roomOffers;
	}

	public RoomOffersDTO() {
		super();
	}

	public List<String> getRoomOffers() {
		return roomOffers;
	}

	public void setRoomOffers(List<String> roomOffers) {
		this.roomOffers = roomOffers;
	}
}
