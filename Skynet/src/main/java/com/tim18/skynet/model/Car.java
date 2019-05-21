package com.tim18.skynet.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "registrationNumber")
	private String registrationNumber;
	@Column(name = "type")
	private String type;
	@Column(name = "gear")
	private String gear;
	@Column(name = "color")
	private String color;

	private Integer passenggers;

	@Column(name = "pricePerDay")
	private Double pricePerDay;

	private String image;

	@ManyToOne
	private Branch branch;
	
	
	@OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<CarReservation> vehicleReservations = new HashSet<CarReservation>();

	public Car() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGear() {
		return gear;
	}

	public void setGear(String gear) {
		this.gear = gear;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	@JsonIgnore
	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Car v = (Car) o;
		if (v.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, v.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + "]";
	}

	public Integer getPassenggers() {
		return passenggers;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public String getImage() {
		return image;
	}

	public Set<CarReservation> getVehicleReservations() {
		return vehicleReservations;
	}

	public void setPassenggers(Integer passenggers) {
		this.passenggers = passenggers;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setVehicleReservations(Set<CarReservation> vehicleReservations) {
		this.vehicleReservations = vehicleReservations;
	}

}
