package com.tim18.skynet.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class FastRoomReservation {
	@Id
	@GeneratedValue
	private Long id;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id", referencedColumnName = "id")
	private Room room;
	
	@Column(nullable = false)
	private int discount;
	@Column(nullable = false)
	private Date startDate;
	@Column(nullable = false)
	private Date endDate;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "fast_room_offers", joinColumns = @JoinColumn(name="fast_reservation_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="offer_id", referencedColumnName="id"))
	private List<HotelOffer> offers;

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<HotelOffer> getOffers() {
		return offers;
	}

	public void setOffers(List<HotelOffer> offers) {
		this.offers = offers;
	}

	public FastRoomReservation(Room room, int discount, Date startDate, Date endDate, List<HotelOffer> offers) {
		super();
		this.room = room;
		this.discount = discount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.offers = offers;
	}

	public FastRoomReservation() {
		super();
	}
	
	
}
