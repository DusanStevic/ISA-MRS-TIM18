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
import com.tim18.skynet.dto.BranchDTO;

@Entity
public class Branch {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "city")
	private String city;

	@Column(name = "address")
	private String address;
	
	@ManyToOne(fetch = FetchType.EAGER)
	RentACar rentacar;
	
	
	public Branch() {
		
	}
	
	public Branch(BranchDTO dto) {
		this.city=dto.getCity();
		this.address=dto.getAddress();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public RentACar getRentacar() {
		return rentacar;
	}

	public void setRentacar(RentACar rentacar) {
		this.rentacar = rentacar;
	}
}
