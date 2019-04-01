package com.tim18.skynet.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	public Car() {
		super();
	}
	public Car(Long id, String registrationNumber, String type, String gear) {
		super();
		this.id = id;
		this.registrationNumber = registrationNumber;
		this.type = type;
		this.gear = gear;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
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

	
	
	
}
