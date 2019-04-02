package com.tim18.skynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.dto.AdminDTO;
import com.tim18.skynet.model.Hotel;
import com.tim18.skynet.model.HotelAdmin;

@RestController
public class HotelAdminController {
	
	@RequestMapping(value = "/addHotelAdmin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public HotelAdmin createHotelAdmin(@RequestBody AdminDTO admin) {
		Hotel hotel= null;
		/*
		List<Hotel> list = new HotelController().getAllHotels();
		for(Hotel h : list){
			if(h.getName().equals(admin.getCompany())){
				hotel = h;
			}
		}
		*/
		HotelAdmin user = new HotelAdmin(admin.getName(), admin.getSurname(), admin.getUsername(), admin.getEmail(), admin.getPassword(), hotel);
		//return service.save(user);
		System.out.println(user.getName());
		return user;
	}
}
