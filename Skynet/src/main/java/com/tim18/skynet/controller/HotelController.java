package com.tim18.skynet.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.dto.CompanyDTO;
import com.tim18.skynet.model.Hotel;
import com.tim18.skynet.model.RentACar;
import com.tim18.skynet.service.impl.HotelServiceImpl;

@RestController
public class HotelController {

	@Autowired
	private HotelServiceImpl hotelService;
	
	@RequestMapping( value="/api/hotel",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Hotel createHotel(@Valid @RequestBody Hotel hotel) {
		return hotelService.save(hotel);
	}
	
}
