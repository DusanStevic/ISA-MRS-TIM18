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

import io.jsonwebtoken.lang.Objects;

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
	
	@OneToMany(mappedBy = "rac", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Branch> branches = new HashSet<Branch>();

	@OneToMany(mappedBy = "racservice", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<RACSpecialOffer> specialOffers = new HashSet<RACSpecialOffer>();
		


		public Set<Branch> getBranches() {
			return branches;
		}
		
		@JsonIgnore
		public Set<Car> getVehicles() {
			Set<Car> vehs = new HashSet<Car>();
			
			for (Branch branch : this.branches) {
				for (Car vehicle : branch.getVehicles()) {
					vehs.add(vehicle);
				}
			}
			return vehs;
		}

		public void setBranches(Set<Branch> branches) {
			this.branches = branches;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(id);
		}

		@Override
		public String toString() {
			return "Rent a car [id=" + id + "]";
		}

		public void addBranch(Branch b) {
			this.branches.add(b);
		}

		public Set<RACSpecialOffer> getSpecialOffers() {
			return specialOffers;
		}

		public void setSpecialOffers(Set<RACSpecialOffer> specialOffers) {
			this.specialOffers = specialOffers;
		}
	


	public RentACar(Long id, String name, String address, String description, Set<Branch> branchs) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.branches = branchs;
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
		this.branches = branchs;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Set<Branch> getBranchs() {
		return branches;
	}
	public void setBranchs(Set<Branch> branchs) {
		this.branches = branchs;
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
