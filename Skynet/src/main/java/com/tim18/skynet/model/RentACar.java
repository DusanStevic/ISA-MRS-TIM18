package com.tim18.skynet.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RentACar {
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private String image;
	
	@JsonIgnore
	@OneToMany(mappedBy = "rac", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Branch> branchs = new HashSet<Branch>();

	public RentACar(Long id, String name, String address, String description, Set<Branch> branchs) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.branchs = branchs;
	}
	
	public RentACar(String name, String address, String description, String image) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
		this.image = image;
	}

	public RentACar(Long id, String name, String address, String description, String image, Set<Branch> branchs) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.image = image;
		this.branchs = branchs;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Set<Branch> getBranchs() {
		return branchs;
	}
	public void setBranchs(Set<Branch> branchs) {
		this.branchs = branchs;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public RentACar() {
		super();
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
}
