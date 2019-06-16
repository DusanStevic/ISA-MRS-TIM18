package com.tim18.skynet.model;

import java.util.Date;

public class CarReservationCriteriums {
	private Date startDate;
	private Date endDate;
	private String startCity;
	private String endCity;
	private String carType;
	private Integer passengers;
	private Double startPrice;
	private Double endPrice;
	
	public CarReservationCriteriums(Date startDate, Date endDate, String startCity, String endCity, String carType,
			Integer passengers, Double startPrice, Double endPrice) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.startCity = startCity;
		this.endCity = endCity;
		this.carType = carType;
		this.passengers = passengers;
		this.startPrice = startPrice;
		this.endPrice = endPrice;
	}
	public Double getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}
	public Double getEndPrice() {
		return endPrice;
	}
	public void setEndPrice(Double endPrice) {
		this.endPrice = endPrice;
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
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	public String getEndCity() {
		return endCity;
	}
	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public Integer getPassengers() {
		return passengers;
	}
	public void setPassengers(Integer passengers) {
		this.passengers = passengers;
	}
	
	
	
}
