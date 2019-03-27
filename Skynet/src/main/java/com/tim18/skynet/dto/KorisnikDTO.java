package com.tim18.skynet.dto;

import com.tim18.skynet.model.Korisnik;

public class KorisnikDTO {
	private Long id;
	private String firstName;
	private String lastName;
	
	
	public KorisnikDTO() {
		// TODO Auto-generated constructor stub
	}


	public KorisnikDTO(Long id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public KorisnikDTO(Korisnik korisnik) {
		this(korisnik.getId(), korisnik.getFirstName(),
				korisnik.getLastName());
	}


	public Long getId() {
		return id;
	}


	public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
	}
	
	
	

}
