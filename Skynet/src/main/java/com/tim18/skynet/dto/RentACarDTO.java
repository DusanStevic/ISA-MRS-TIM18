package com.tim18.skynet.dto;

public class RentACarDTO {
		private String name;
		private String adress;
		private String description;
		
		
		
		public RentACarDTO() {
			super();
		}
		public RentACarDTO(String name, String adress, String description) {
			super();
			this.name = name;
			this.adress = adress;
			this.description = description;
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
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	


}
