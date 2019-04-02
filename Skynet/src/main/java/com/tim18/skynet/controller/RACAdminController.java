package com.tim18.skynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.dto.AdminDTO;
import com.tim18.skynet.dto.CompanyDTO;
import com.tim18.skynet.model.RACAdmin;
import com.tim18.skynet.model.RentACar;

@RestController
public class RACAdminController {

	@RequestMapping(value = "/addRACAdmin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public RACAdmin createRACAdmin(@RequestBody AdminDTO admin) {
		RentACar rentacar = null;
		/*
		List<RentACar> list = new RACController().getAllRentAcars();
		for(RentACar r : list){
			if(r.getName().equals(admin.getCompany())){
				rentacar = r;
			}
		}
		*/
		RACAdmin user = new RACAdmin(admin.getName(), admin.getSurname(), admin.getUsername(), admin.getEmail(), admin.getPassword(), rentacar);
		//return service.save(user);
		return user;
	}
}
