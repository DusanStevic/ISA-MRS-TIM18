package com.tim18.skynet.dto;

public class BranchDTO {
	private String name;
	private String adress;
	private String image;
	private Long id;
	
	
	
	public BranchDTO(String name, String adress, String image) {
		super();
		this.name = name;
		this.adress = adress;
		this.image = image;
	}
	
	public Long getId() {
		return id;
	}
	

	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	



}
