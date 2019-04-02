package com.tim18.skynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.dto.AdminDTO;
import com.tim18.skynet.model.Airline;
import com.tim18.skynet.model.AirlineAdmin;

@RestController
public class AirlineAdminController {

	@RequestMapping(value = "/addAirlineAdmin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public AirlineAdmin createAirlineAdmin(@RequestBody AdminDTO admin) {
		Airline airline = null;
		/*
		List<Airline> list = new AirlineController().getAllAirlines();
		for(Airline a : list){
			if(a.getName().equals(admin.getCompany())){
				airline = a;
			}
		}
		*/
		AirlineAdmin user = new AirlineAdmin(admin.getName(), admin.getSurname(), admin.getUsername(), admin.getEmail(), admin.getPassword(), airline);
		return user;
		//return service.save(user);
	}
}
