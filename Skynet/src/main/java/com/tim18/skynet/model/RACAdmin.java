package com.tim18.skynet.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RACAdmin extends User{
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rac_id", referencedColumnName = "id")
	private RentACar rentacar;

	public RACAdmin() {
		super();
	}

	public RentACar getRentacar() {
		return rentacar;
	}

	public void setRentacar(RentACar rentacar) {
		this.rentacar = rentacar;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1892679582107777957L;

}
