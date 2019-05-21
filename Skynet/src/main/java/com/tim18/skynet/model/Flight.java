package com.tim18.skynet.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "flight")
public class Flight {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "startDate", unique = false, nullable = false)
	private Date startDate;
	
	@Column(name = "endDate", unique = false, nullable = false)
	private Date endDate;
	
	@Column(name = "flightDuration", unique = false, nullable = false)
	private int  flightDuration;
	
	@Column(name = "flightLength", unique = false, nullable = false)
	private int flightLength;
	
	@Column(name = "seats", unique = false, nullable = false)
	private int seats;
	
	@OneToOne()
	private Destination startDestination;
	
	@OneToOne()
	private Destination endDestination;
	
	
	@Column(name = "buisinessPrice", unique = false, nullable = true)
	private double businessPrice;
	
	@Column(name = "economicPrice", unique = false, nullable = true)
	private double economicPrice;
	
	@Column(name = "firstClassPrice", unique = false, nullable = true)
	private double firstClassPrice;
	
	public Flight() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	

	
	public Flight(Date startDate, Date endDate, int flightDuration, int flightLength, int seats,
			Destination startDestination, Destination endDestination, double businessPrice, double economicPrice,
			double firstClassPrice) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.flightDuration = flightDuration;
		this.flightLength = flightLength;
		this.seats = seats;
		this.startDestination = startDestination;
		this.endDestination = endDestination;
		this.businessPrice = businessPrice;
		this.economicPrice = economicPrice;
		this.firstClassPrice = firstClassPrice;
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

	public int getFlightDuration() {
		return flightDuration;
	}

	public void setFlightDuration(int flightDuration) {
		this.flightDuration = flightDuration;
	}

	public int getFlightLength() {
		return flightLength;
	}

	public void setFlightLength(int flightLength) {
		this.flightLength = flightLength;
	}


	public double getBusinessPrice() {
		return businessPrice;
	}

	public void setBusinessPrice(double businessPrice) {
		this.businessPrice = businessPrice;
	}

	public double getEconomicPrice() {
		return economicPrice;
	}

	public void setEconomicPrice(double economicPrice) {
		this.economicPrice = economicPrice;
	}

	public double getFirstClassPrice() {
		return firstClassPrice;
	}

	public void setFirstClassPrice(double firstClassPrice) {
		this.firstClassPrice = firstClassPrice;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}



	public Destination getStartDestination() {
		return startDestination;
	}



	public void setStartDestination(Destination startDestination) {
		this.startDestination = startDestination;
	}

	public Destination getEndDestination() {
		return endDestination;
	}



	public void setEndDestination(Destination endDestination) {
		this.endDestination = endDestination;
	}






	public int getSeats() {
		return seats;
	}






	public void setSeats(int seats) {
		this.seats = seats;
	}






	@Override
	public String toString() {
		return "Flight [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", flightDuration="
				+ flightDuration + ", flightLength=" + flightLength + ", seats=" + seats + ", startDestination="
				+ startDestination + ", endDestination=" + endDestination + ", businessPrice=" + businessPrice
				+ ", economicPrice=" + economicPrice + ", firstClassPrice=" + firstClassPrice + "]";
	}




	

	

}
