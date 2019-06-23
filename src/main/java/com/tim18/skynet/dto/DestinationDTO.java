package com.tim18.skynet.dto;

import javax.persistence.Column;

public class DestinationDTO {
	
	
	private String name;
	private String description;
	private String coordinates;
	
	public DestinationDTO() {
		// TODO Auto-generated constructor stub
	}

	public DestinationDTO(String name, String description, String coordinates) {
		super();
		this.name = name;
		this.description = description;
		this.coordinates = coordinates;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	
	

}
